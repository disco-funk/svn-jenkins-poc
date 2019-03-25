#include <iostream>
#include "Module1.hpp"
using namespace std;

int main() 
{
    Module1 module = Module1();
    cout << "Output from " << module.moduleName() << " - something";
    return 0;
}