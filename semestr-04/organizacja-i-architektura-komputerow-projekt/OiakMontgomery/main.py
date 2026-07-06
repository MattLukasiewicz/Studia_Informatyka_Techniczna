def montgomery_multiplication(A, B, M):
    """
    : A: pierwsza liczba w reprezentacji Montgomery'ego
    : B: druga liczba w reprezentacji Montgomery'ego
    : M: liczba modulo (musi być nieparzysta)
    : wynik mnożenia w reprezentacji Montgomery'ego
    """
    if M % 2 == 0:
        raise ValueError("Modulo M musi być nieparzyste")

    # Wybór R jako potęgi 2 większej od M
    R = 1 << (M.bit_length())

    # Obliczenie M' = -M^(-1) mod R
    M_inv = pow(M, -1, R)
    M_prime = (-M_inv) % R

    # Obliczenia Montgomery’ego
    T = A * B
    m = (T * M_prime) % R
    U = (T + m * M) // R

    return U if U < M else U - M


def montgomery_reduce(x, M, R):
    """
    Konwersja liczby do reprezentacji Montgomery'ego.
    : x: liczba do konwersji
    : M: liczba modulo
    : R: potęga dwójki większa niż M
    :return: x * R mod M
    """
    return (x * R) % M


def montgomery_to_normal(x, M, R):
    """
    Konwersja liczby z reprezentacji Montgomery'ego do normalnej.
    : x: liczba w reprezentacji Montgomery'ego
    : M: liczba modulo
    : R: potęga dwójki większa niż M
    :return: x * R^(-1) mod M
    """
    R_inv = pow(R, -1, M)
    return (x * R_inv) % M


A = 123456
B = 654321
M = 999983

R = 1 << (M.bit_length())

# Konwersja do reprezentacji Montgomery'ego
A_mont = montgomery_reduce(A, M, R)
B_mont = montgomery_reduce(B, M, R)

# Mnożenie Montgomery'ego
result_mont = montgomery_multiplication(A_mont, B_mont, M)

# Konwersja wyniku do normalnej postaci
result = montgomery_to_normal(result_mont, M, R)
print("Wynik mnożenia modulo M:", result)