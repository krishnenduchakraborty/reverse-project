#include <stdio.h>

int main() {
    int n = 100;
    for(int m = 1; n >= m; n--){
        if(n%2 == 0) {
            printf("Even Number\n");
        }
        else {
            printf("Odd Number\n");
        }
    }
    return 0;
}
