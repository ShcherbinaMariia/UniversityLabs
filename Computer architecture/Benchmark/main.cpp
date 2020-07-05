#include<vector>
#include "graphics.cpp"
#include "logic.cpp"
#include "functions.cpp"

vector<double> result(20, 0);
double maxOpPerSecond;

double maxElement(vector<double> v)
{
	double maxx = -1;
	for (int i = 0; i < v.size(); i++)
		if (v[i] > maxx){
			maxx = v[i];
		}
	return maxx;
}

void getResults(){

	result[0] = getTime(&addition<int>, &assignment<int>);
	result[1] = getTime(&subtraction<int>, &assignment<int>);
	result[2] = getTime(&multiplication<int>, &assignment<int>);
	result[3] = getTime(&division<int>, &assignment<int>);
	result[4] = getTime(&addition<long>, &assignment<long>);
	result[5] = getTime(&subtraction<long>, &assignment<long>);
	result[6] = getTime(&multiplication<long>, &assignment<long>);
	result[7] = getTime(&division<long>, &assignment<long>);
	result[8] = getTime(&addition<double>, &assignment<double>);
	result[9] = getTime(&subtraction<double>, &assignment<double>);
	result[10] = getTime(&multiplication<double>, &assignment<double>);
	result[11] = getTime(&division<double>, &assignment<double>);
	result[12] = getTime(&addition<uint>, &assignment<uint>);
	result[13] = getTime(&subtraction<uint>, &assignment<uint>);
	result[14] = getTime(&multiplication<uint>, &assignment<uint>);
	result[15] = getTime(&division<uint>, &assignment<uint>);
	result[16] = getTime(&addition<char32_t>, &assignment<char32_t>);
	result[17] = getTime(&subtraction<char32_t>, &assignment<char32_t>);
	result[18] = getTime(&multiplication<char32_t>, &assignment<char32_t>);
	result[19] = getTime(&division<char32_t>, &assignment<char32_t>);
	maxOpPerSecond = maxElement(result);
}

void drawTable( ){

	getTableHeader();
	getTableRow('+', "int", result[0]);
	getTableRow('-', "int", result[1]);
	getTableRow('*', "int", result[2]);
	getTableRow('/', "int", result[3]);
	getTableRow('+', "long", result[4]);
	getTableRow('-', "long", result[5]);
	getTableRow('*', "long", result[6]);
	getTableRow('/', "long", result[7]);
	getTableRow('+', "double", result[8]);
	getTableRow('-', "double", result[9]);
	getTableRow('*', "double", result[10]);
	getTableRow('/', "double", result[11]);
	getTableRow('+', "uint", result[12]);
	getTableRow('-', "uint", result[13]);
	getTableRow('*', "uint", result[14]);
	getTableRow('/', "uint", result[15]);
	getTableRow('+', "char32_t", result[16]);
	getTableRow('-', "char32_t", result[17]);
	getTableRow('*', "char32_t", result[18]);
	getTableRow('/', "char32_t", result[19]);
} 

int main(){
	getResults();
	drawTable();
	return 0;
}