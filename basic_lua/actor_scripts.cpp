#include "actor_scripts.h"
#include "lua.h"

const char *script_filename = "actions.lua";

static lua_State *L;

static int l_set_actor_next_target(lua_State *L) {
    int actor = (int) luaL_checknumber(L, -2);
    int action = (int) luaL_checknumber(L, -1);
    printf("Setting actor %d to do action %d\n", actor, action);
    return 0;
}

namespace actor_scripts {

int init() {
    L = lua_open();
    luaL_openlibs(L);

    if (luaL_dofile(L, script_filename) != 0) {
        return 1;
    }

    lua_pushcfunction(L, l_set_actor_next_target);
    lua_setglobal(L, "set_actor_next_target");

    return 0;
}

int actor_next_target(int actor) {
    lua_getglobal(L, "actor_next_target");
    lua_pushnumber(L, actor);

    if (lua_pcall(L, 1, 0, 0) != 0) {
        fprintf(stderr, lua_tostring(L, -1));
        return 1;
    }

    return 0;
}

}
