import time

class BoardArduinoCommunicator: 
	import serial

	def __init__(self, ARDUINO_PORT):
		self.arduino = self.serial.Serial(port=ARDUINO_PORT, baudrate=9600, timeout=2.5)
		print("Placa ARDUINO alcanzada")


	def enviarDatosToArduino(self,simulacionJson, sataBoardClient):
		try:
			print(simulacionJson)
			simulacionJsonBytes = simulacionJson.encode()
			self.arduino.write(simulacionJsonBytes)
			time.sleep(1.2)
			mensajeArduino = str(self.arduino.readlines()[0])
			print(f"Secuencias enviadas a ARDUINO")
			print(f"Mensaje de arduino: {mensajeArduino}")
			# raise ERROR si no es exitosa esta parte
			# ENVIAR MENSAJES PARA QUE ARDUINO NOS PUEDA RESPONDER Y ENVIAR DATOS
			while True:
				time.sleep(1)
				self.arduino.write("sendCaudalToSataBoard".encode())
				mensajeArduino = float(self.arduino.readlines()[0])
				print(f"Mensaje de arduino: {mensajeArduino} mililitros por minuto")
				if mensajeArduino < 0:
					print("VALOR NEGATIVO RECIBIDO. SIMULACION EJECUTADA EXITOSAMENTE")
					cantidadAgua = 60.0
					break
				sataBoardClient.sendCaudalToCentralCore(mensajeArduino)
		except Exception as e:
			print("\nERROR EN EL PROCESO DE COMUNICACION CON ARDUINO")
			print(e)
			cantidadAgua = 0.0
		finally:
			sataBoardClient.sendAvisoTerminoToCentralCore(cantidadAgua)