import numpy as np
import matplotlib.pyplot as plt
from scipy import signal

# Parametry sygnału
czas_trwania = 1  # w sekundach
amplituda = 1
czestotliwosc_sygnalu1 = 50  # w Hz
czestotliwosc_sygnalu2 = 150  # w Hz
czestotliwosc_sygnalu3 = 300  # w Hz
czestotliwosc_probkowania = 1000  # w Hz

# Generowanie czasu i sygnałów
N = czas_trwania * czestotliwosc_probkowania
czas = np.linspace(0, czas_trwania, N, endpoint=False)
sygnal1 = amplituda * np.sin(2 * np.pi * czestotliwosc_sygnalu1 * czas)
sygnal2 = amplituda * np.sin(2 * np.pi * czestotliwosc_sygnalu2 * czas)
sygnal3 = amplituda * np.sin(2 * np.pi * czestotliwosc_sygnalu3 * czas)

# Sygnał końcowy
sygnalkoncowy = sygnal1 + sygnal2 + sygnal3

# Filtry
b_low, a_low = signal.butter(N=4, Wn=100, btype="low", fs=czestotliwosc_probkowania)
b_high, a_high = signal.butter(N=4, Wn=200, btype="high", fs=czestotliwosc_probkowania)
b_band, a_band = signal.butter(N=4, Wn=[100, 200], btype="band", fs=czestotliwosc_probkowania)

lowpass = signal.filtfilt(b_low, a_low, sygnalkoncowy)
highpass = signal.filtfilt(b_high, a_high, sygnalkoncowy)
bandpass = signal.filtfilt(b_band, a_band, sygnalkoncowy)

# FFT
freqs = np.fft.rfftfreq(N, d=1/czestotliwosc_probkowania)
fft_orig = np.abs(np.fft.rfft(sygnalkoncowy))
fft_low = np.abs(np.fft.rfft(lowpass))
fft_high = np.abs(np.fft.rfft(highpass))
fft_band = np.abs(np.fft.rfft(bandpass))

# Wykresy w subplocie
fig, axs = plt.subplots(8,  figsize=(14, 16))

# Oryginalny sygnał
axs[0].plot(czas, sygnalkoncowy, label="Oryginalny sygnał", alpha=0.7, color='blue')
axs[0].set_title("Oryginalny sygnał")
axs[0].set_xlabel("Czas [s]")
axs[0].set_ylabel("Amplituda")
axs[0].legend()
axs[0].grid()

# FFT Oryginalnego sygnału
axs[1].plot(freqs, fft_orig, label="FFT Oryginalny", color='blue')
axs[1].set_title("FFT Oryginalnego sygnału")
axs[1].set_xlabel("Częstotliwość [Hz]")
axs[1].set_ylabel("Amplituda")
axs[1].legend()
axs[1].grid()

# Low-pass
axs[2].plot(czas, lowpass, label="Low-pass", alpha=0.7, color='green')
axs[2].set_title("Low-pass filtrowany sygnał")
axs[2].set_xlabel("Czas [s]")
axs[2].set_ylabel("Amplituda")
axs[2].legend()
axs[2].grid()

axs[3].plot(freqs, fft_low, label="FFT Low-pass", color='green')
axs[3].set_title("FFT Low-pass filtrowanego sygnału")
axs[3].set_xlabel("Częstotliwość [Hz]")
axs[3].set_ylabel("Amplituda")
axs[3].legend()
axs[3].grid()

# High-pass
axs[4].plot(czas, highpass, label="High-pass", alpha=0.7, color='red')
axs[4].set_title("High-pass filtrowany sygnał")
axs[4].set_xlabel("Czas [s]")
axs[4].set_ylabel("Amplituda")
axs[4].legend()
axs[4].grid()

axs[5].plot(freqs, fft_high, label="FFT High-pass", color='red')
axs[5].set_title("FFT High-pass filtrowanego sygnału")
axs[5].set_xlabel("Częstotliwość [Hz]")
axs[5].set_ylabel("Amplituda")
axs[5].legend()
axs[5].grid()

# Band-pass
axs[6].plot(czas, bandpass, label="Band-pass", alpha=0.7, color='purple')
axs[6].set_title("Band-pass filtrowany sygnał")
axs[6].set_xlabel("Czas [s]")
axs[6].set_ylabel("Amplituda")
axs[6].legend()
axs[6].grid()

axs[7].plot(freqs, fft_band, label="FFT Band-pass", color='purple')
axs[7].set_title("FFT Band-pass filtrowanego sygnału")
axs[7].set_xlabel("Częstotliwość [Hz]")
axs[7].set_ylabel("Amplituda")
axs[7].legend()
axs[7].grid()

plt.tight_layout()
plt.show()

# Spectrogram
plt.figure(figsize=(10, 6))
plt.specgram(sygnalkoncowy, Fs=czestotliwosc_probkowania, NFFT=256, noverlap=128, cmap='viridis')
plt.title("Spectrogram Oryginalnego Sygnału")
plt.xlabel("Czas [s]")
plt.ylabel("Częstotliwość [Hz]")
plt.colorbar(label="Amplituda")
plt.tight_layout()
plt.show()
