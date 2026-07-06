import numpy as np
import matplotlib.pyplot as plt
import time

def dft(signal):
    """Implementacja ręczna Dyskretnej Transformaty Fouriera (DFT)."""
    N = len(signal)
    n = np.arange(N)
    k = n.reshape((N, 1))
    exponential = np.exp(-2j * np.pi * k * n / N)
    result = np.dot(exponential, signal)
    return result

# Generowanie sygnału
fs = 1000  # Częstotliwość próbkowania (Hz)
t = np.linspace(0, 1, fs, endpoint=False)  # Wektor czasu (1 sekunda, fs próbek)
f1, A1 = 50, 1  # Częstotliwość i amplituda pierwszego sinusa
f2, A2 = 120, 0.5  # Częstotliwość i amplituda drugiego sinusa

# Oryginalny sygnał i sygnał z dodatkowymi składowymi
original_signal = np.sin(2 * np.pi * f1 * t)
added_signal = original_signal + A2 * np.sin(2 * np.pi * f2 * t)

# DFT
start_time_dft = time.time()
dft_result = dft(added_signal)
end_time_dft = time.time()

# FFT (z biblioteki numpy)
start_time_fft = time.time()
fft_result = np.fft.fft(added_signal)
end_time_fft = time.time()

# Czas wykonania
dft_time = end_time_dft - start_time_dft
fft_time = end_time_fft - start_time_fft

# Amplituda (moduł) pierwszej połowy wyników
dft_magnitude = np.abs(dft_result)[:fs // 2]
fft_magnitude = np.abs(fft_result)[:fs // 2]

# Oś częstotliwości
frequencies = np.fft.fftfreq(fs, 1/fs)[:fs // 2]

# Wizualizacja
plt.figure(figsize=(12, 8))

# Oryginalny sygnał i sygnał z dodatkowymi składowymi
plt.subplot(3, 1, 1)
plt.plot(t, added_signal, label="Sygnał z dodatkowymi składowymi")
plt.plot(t, original_signal, label="Oryginalny sygnał", linestyle='dashed')
plt.title("Sygnał czasowy")
plt.xlabel("Czas [s]")
plt.ylabel("Amplituda")
plt.legend()

# Wynik DFT
plt.subplot(3, 1, 2)
plt.plot(frequencies, dft_magnitude, label="DFT (manualna implementacja)")
plt.title("Wynik DFT (pierwsza połowa)")
plt.xlabel("Częstotliwość [Hz]")
plt.ylabel("Amplituda")
plt.legend()

# Wynik FFT
plt.subplot(3, 1, 3)
plt.plot(frequencies, fft_magnitude, label="FFT (biblioteka numpy)", color='red')
plt.title("Wynik FFT (pierwsza połowa)")
plt.xlabel("Częstotliwość [Hz]")
plt.ylabel("Amplituda")
plt.legend()

plt.tight_layout()
plt.show()

# Porównanie czasów
print(f"Czas wykonania DFT: {dft_time:.5f} s")
print(f"Czas wykonania FFT: {fft_time:.5f} s")
print(f"FFT jest około {dft_time / fft_time:.2f} razy szybszy od DFT.")
