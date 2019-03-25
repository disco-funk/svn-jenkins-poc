#include <iostream>
#include "Module3.hpp"
using namespace std;

int main()
{
    Module3 module = Module3();
    cout << "Output from " << module.moduleName() << " - thirdthing";
    return 0;
}