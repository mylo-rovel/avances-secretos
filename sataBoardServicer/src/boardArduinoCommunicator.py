class BoardArduinoCommunicator: 
	import serial
	import time

	def __init__(self):
		self.ser = self.serial.serial("/dev/ttyUSB0", 9600)
		print("fui instanciado")

	def enviarDatosToArduino(secuenciasJson):
		try:
			comando = comando + "\n"
			comandoBytes = comando.encode()
			self.ser.write(comandoBytes)
			time.sleep(0.1)
			read = self.ser.readline()
			print(read)
		except KeyboardInterrupt:
			print("\nInterrupcion por teclado")
		except ValueError as ve:
			print(ve)
			print("Otra interrupcion")
		finally:
				self.ser.close()