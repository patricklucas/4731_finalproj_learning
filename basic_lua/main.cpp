#include <stdio.h>

#include "actor_scripts.h"

int main(int argc, char *argv[]) {
    if (actor_scripts::init() != 0) {
        printf("Error during init :(\n");
        return 1;
    }

    actor_scripts::actor_next_target(3);
    actor_scripts::actor_next_target(4);
    actor_scripts::actor_next_target(5);
}
