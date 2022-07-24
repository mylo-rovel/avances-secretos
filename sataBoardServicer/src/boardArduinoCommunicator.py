import time

class BoardArduinoCommunicator: 
	import serial

	def __init__(self, ARDUINO_PORT):
		self.arduino = self.serial.Serial(port=ARDUINO_PORT, baudrate=9600)
		print("Placa ARDUINO alcanzada")


	def enviarDatosToArduino(self,secuenciasJson):
		try:
			self.arduino.write(secuenciasJson.encode())
			msgFromArduino = self.arduino.readlines()
			print(f"Mensaje de arduino: {msgFromArduino}")		
		except Exception as e:
			print("ERROR AL ENVIAR DATOS A ARDUINO\n")
			print(e)
		finally:
			# self.arduino.close()
			pass