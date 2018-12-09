#include <iostream>
#include <iomanip>
#include <string>
#include "main.hpp"

int OP_COLUMN_WIDTH = 3;
int TYPE_COLUMN_WIDTH = 8;
int OP_PER_SECOND_COLUMN_WIDTH = 15;
int DIAGRAM_COLUMN_WIDTH = 54;
int PERCENT_COLUMN_WIDTH = 5;

extern double maxOpPerSecond;

std::string getDiagram(double opPerSecond){
   if (opPerSecond < 0) return "";
	return std::string((int) ((opPerSecond / maxOpPerSecond) * DIAGRAM_COLUMN_WIDTH) , '#');
}

std::string getPercent(double opPerSecond){
   return std::to_string((opPerSecond / maxOpPerSecond) * 100) + "%";
}

void getTableHeader(){
	std::cout << std::setw(OP_COLUMN_WIDTH) << std::left << "OP" << "  "
         << std::setw(TYPE_COLUMN_WIDTH) << std::left << "TYPE" << "   "
         << std::setw(OP_PER_SECOND_COLUMN_WIDTH) << std::left << "OP_PER_SECOND" << " " 
         << std::setw(PERCENT_COLUMN_WIDTH) << std::left << "PERCENT" << " "
         << std::setw(DIAGRAM_COLUMN_WIDTH) << std::left <<  "DIAGRAM" << std::endl;
}

void getTableRow(char op, std::string type, double opPerSecond){
	std::cout.precision(2);
	std::cout << std::setw(OP_COLUMN_WIDTH) << std::left << op << "  "
         << std::setw(TYPE_COLUMN_WIDTH) << std::left << type << "   "
         << std::setw(OP_PER_SECOND_COLUMN_WIDTH) << std::left <<std::scientific << opPerSecond << " "
         << std::setw(PERCENT_COLUMN_WIDTH) << std::left << std::fixed << getPercent(opPerSecond) << " "
         << std::setw(DIAGRAM_COLUMN_WIDTH) << std::left << getDiagram(opPerSecond) << std::endl;
}