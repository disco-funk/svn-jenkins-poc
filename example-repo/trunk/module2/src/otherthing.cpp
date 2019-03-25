#include <iostream>
#include "Module2.hpp"
using namespace std;

int main()
{
    Module2 module = Module2();
    cout << "Output from " << module.moduleName() << " - otherthing";
    return 0;
}