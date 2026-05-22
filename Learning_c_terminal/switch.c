#include <stdio.h>

int main() {
    int options;
 while(1) {
    printf("Enter option: ");
    scanf("%d", &options);
    switch (options) {
        case 1 :
            printf("Hello World!\n");
            break;
        case 2 :
            printf("Homeless!\n");
            break;
        case 3 :
            printf("Game Over!\n");
            break;
        case 0 :
            printf("Program terminated\n");
            return 0;
        default:
            printf("Invalid option!\n");
            break;
    }
 }
  return 0;
}
