CXX = g++
CXXFLAGS = -Wall -std=c++17 -O2
TARGET = pea_projekt

# Tutaj podpowiadamy, zeby szukal plików .cpp wewnątrz folderu src/
SRC = $(wildcard src/*.cpp)

all: $(TARGET)

$(TARGET): $(SRC)
	$(CXX) $(CXXFLAGS) $(SRC) -o $(TARGET)

clean:
	rm -f $(TARGET) *.exe