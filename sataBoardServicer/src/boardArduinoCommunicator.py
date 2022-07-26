import time

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
			mensajeArduino = self.arduino.readlines()[0]
			print(f"Secuencias enviadas a ARDUINO")
			print(f"Mensaje de arduino: {mensajeArduino}")
			# ENVIAR MENSAJES PARA QUE ARDUINO NOS PUEDA RESPONDER Y ENVIAR DATOS
			while True:
				time.sleep(1.2)
				self.arduino.write("sendCaudalToSataBoard".encode())
				mensajeArduino = float(self.arduino.readlines()[0])
				print(f"Mensaje de arduino: {mensajeArduino} litros por minuto")
				# USAR OTROS HILOS?
				sataBoardClient.sendCaudalToCentralCore(mensajeArduino)
				if mensajeArduino == "FinishedExecution": 
					break
					sataBoardClient.sendAvisoTerminoToCentralCore(60)
		except Exception as e:
			print("ERROR AL ENVIAR DATOS A ARDUINO\n")
			print(e)
		finally:
			# self.arduino.close()
			pass