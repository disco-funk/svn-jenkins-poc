#include <iostream>
#include "Module1.hpp"
#include "Module2.hpp"
using namespace std;

int main() 
{
    Module1 m1;
    Module2 m2;
    cout << "Output from Dependent modules: " << m1.moduleName() << " & " << m2.moduleName();
    return 0;
}