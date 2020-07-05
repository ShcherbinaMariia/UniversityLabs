#include <thread>
#include <iostream>
#include <mutex>
#include <cstdlib>
#include <chrono> 
using namespace std;

int n, m, k;
int **a, **b, **result;
int mod = 1e9 + 7;
mutex out;


int** allocate(int n, int m){
	int** a = new int*[n];
	for (int i = 0; i < n; i++)
		a[i] = new int[m];
	return a;
}

void deallocate(int** a, int n){
	for (int i = 0; i < n; i++)
		delete[] a[i];
	delete a;
}

void fillRandom(int** a, int n, int m){
	for (int i = 0; i < n; i++){
		for (int j = 0; j < m; j++){
			a[i][j] = rand() % 100;
		}
	}
}

// multiplies two vectors of length m a[n][1..m] and b[1..m][k]
void vectorMultiplication(int n, int k){
	int sum = 0;
	for (int i = 0; i < m; i++){
		sum += a[n][i] * b[i][k];
		sum %= mod;
	}
	result[n][k] = sum;
	out.lock();
	cout << n <<  " " << k << "\n";
	out.unlock();
}

void multithreadedMultiplication(){
	thread t[n*k];
	for (int i = 0; i < n; i++){
		for (int j = 0; j < k; j++){
			t[i * k + j] = thread(vectorMultiplication, i, j);
		}
	}

	for (int i = 0; i < n*k; i++){
		t[i].join();
	}
}

void sequentialMultiplication(){
	for (int i = 0; i < n; i++){
		for (int j = 0; j < k; j++){
			vectorMultiplication(i, j);
		}
	}
}

void execute(void (*f)()){
	auto start = chrono::high_resolution_clock::now();
	f();
	auto stop = chrono::high_resolution_clock::now();
	auto duration = chrono::duration_cast<chrono::microseconds>(stop - start); 
	cout << "Execution time: " << duration.count() << " microseconds\n";
}

int main(){
	srand(time(NULL));

	cout << "Enter matrix dimensions n, m, k: A[n*m] and B[m*k]\n";
	cin >> n >> m >> k;

	a = allocate(n, m);
	b = allocate(m, k);
	result = allocate(n, k);

	fillRandom(a, n, m);
	fillRandom(b, m, k);

	cout << "Sequential version:\n";
	execute(sequentialMultiplication);

	cout << "Multithreaded version:\n";
	execute(multithreadedMultiplication);
	
	deallocate(a, n);
	deallocate(b, m);
	deallocate(result, n);

	return 0;
}
