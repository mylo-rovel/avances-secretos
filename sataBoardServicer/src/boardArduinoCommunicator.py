

class BoardArduinoCommunicator: 
	import serial
	import time

	def __init__(self, ARDUINO_PORT):
		self.arduino = self.serial.Serial(port=ARDUINO_PORT, baudrate=9600, timeout=.1)
		print("Placa ARDUINO alcanzada")


	def enviarDatosToArduino(secuenciasJson):
		try:
			comando = comando + "\n"
			comandoBytes = comando.encode()
			self.arduino.write(comandoBytes)
			time.sleep(0.1)
			read = self.arduino.readline()
			print(read)
		except:
			print("ERROR AL ENVIAR DATOS A ARDUINO")
		finally:
			# self.arduino.close()
			pass