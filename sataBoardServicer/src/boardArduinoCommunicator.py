import time

class BoardArduinoCommunicator: 
	import serial

	def __init__(self, ARDUINO_PORT):
		self.arduino = self.serial.Serial(port=ARDUINO_PORT, baudrate=9600, timeout=2.5)
		print("Placa ARDUINO alcanzada")


	def enviarDatosToArduino(self,simulacionJson):
		try:
			simulacionJsonBytes = simulacionJson.encode()
			self.arduino.write(simulacionJsonBytes)
			time.sleep(0.1)
			msgFromArduino = self.arduino.readlines()
			print(f"Mensaje de arduino: {msgFromArduino}")
		except Exception as e:
			print("ERROR AL ENVIAR DATOS A ARDUINO\n")
			print(e)
		finally:
			# self.arduino.close()
			pass