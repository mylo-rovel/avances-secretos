import serial
import time
ser = serial.Serial("/dev/ttyUSB0", 9600)

try:
	while True:
		comando = input("Ingresar comando (abrir/cerrar):")
		comando = comando + "\n"
		comandoBytes = comando.encode()
		ser.write(comandoBytes)
		time.sleep(0.1)
		read = ser.readline()
		print(read)
except KeyboardInterrupt:
	print("\nInterrupcion por teclado")
except ValueError as ve:
	print(ve)
	print("Otra interrupcion")
finally:
	ser.close()
