import time
from datetime import datetime

class BoardArduinoCommunicator: 
	import serial

	def __init__(self, ARDUINO_PORT):
		self.arduino = self.serial.Serial(port=ARDUINO_PORT, baudrate=9600, timeout=2.5)
		print("Placa ARDUINO alcanzada")


	def enviarDatosToArduino(self,simulacionJson, sataBoardClient):
		try:
			simulacionJsonBytes = simulacionJson.encode()
			self.arduino.write(simulacionJsonBytes)
			time.sleep(1.2)
			# caudalFromArduino = self.arduino.readlines()
			# print(f"Mensaje de arduino: {caudalFromArduino} litros por minuto")
			mensajeArduino = self.arduino.readlines()[0]
			print(f"Secuencias enviadas a ARDUINO")
			print(f"Mensaje de arduino: {mensajeArduino}")
			if mensajeArduino != "JSON SETUP FINISHED": break
			while True:
				time.sleep(1.2)
				self.arduino.write("sendCaudalToSataBoard".encode())
				mensajeArduino = self.arduino.readlines()
				print(f"Mensaje de arduino: {mensajeArduino}")
				if mensajeArduino == "FinishedExecution": break
				# mensajeArduino = float(self.arduino.readlines()[0])
				# print(f"Mensaje de arduino: {mensajeArduino} litros por minuto")
				# sataBoardClient.sendCaudalToCentralCore(mensajeArduino)
		except Exception as e:
			print("ERROR AL ENVIAR DATOS A ARDUINO\n")
			print(e)
		finally:
			# self.arduino.close()
			pass