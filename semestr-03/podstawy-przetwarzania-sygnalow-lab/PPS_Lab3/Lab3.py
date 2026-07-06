import numpy as np
import matplotlib.pyplot as plt
#Stworzyc i wyswietlic sygnal sinosoidlany

czas_trwania = 3 #s
amplituda = 5
czestotliwosc_sygnalu1 = 7 #Hz
czestotliwosc_sygnalu2 = 8 #Hz
czestotliwosc_sygnalu3 = 9 #Hz
czestotliwosc_probkowania = 100#Hz

czas = np.linspace(0,czas_trwania,czas_trwania*czestotliwosc_probkowania,endpoint=False)


sygnal1 = amplituda*np.sin(2*np.pi * czestotliwosc_sygnalu1*czas)
sygnal2 = amplituda*np.sin(2*np.pi * czestotliwosc_sygnalu2*czas)
sygnal3 = amplituda*np.sin(2*np.pi * czestotliwosc_sygnalu3*czas)
sygnalkoncowy = sygnal1+sygnal2+sygnal3




fix,axs = plt.subplots(2,1, sharex =True, figsize=(8,8))
axs[0].plot(czas,sygnal1)
axs[0].set_title("Zadanie 1")
axs[1].plot(czas,sygnalkoncowy)
axs[1].set_title("Zadanie 2")
plt.xlabel("czas")
plt.show()



