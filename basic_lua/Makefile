CFLAGS=
LIBS=-llua

BIN=awesome

.PHONY: all
all: $(BIN)

$(BIN): main.o actor_scripts.o
	g++ -o $@ $(LIBS) $^

main.o: main.cpp
	g++ -o $@ $(CFLAGS) -c $^

actor_scripts.o: actor_scripts.cpp
	g++ -o $@ $(CFLAGS) -c $^

.PHONY: clean
clean:
	rm -rf $(BIN) *.o
