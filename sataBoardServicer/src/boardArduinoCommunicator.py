class BoardArduinoCommunicator: 
	import serial
	import time

	def __init__(self):
		self.ser = serial.Serial("/dev/ttyUSB0", 9600)
		print("fui instanciado")

	def enviarDatosToArduino(secuenciasJson):
		try:
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