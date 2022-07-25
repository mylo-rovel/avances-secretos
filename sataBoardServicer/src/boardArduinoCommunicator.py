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
			time.sleep(1.2)
			msgFromArduino = float(self.arduino.readlines()[0])
			print(f"Mensaje de arduino: {msgFromArduino} litros por minuto")
			while True:
				time.sleep(1.2)
				self.arduino.write("sendCaudalToRaspi".encode())
				msgFromArduino = float(self.arduino.readlines()[0])
				print(f"Mensaje de arduino: {msgFromArduino} litros por minuto")
		except Exception as e:
			print("ERROR AL ENVIAR DATOS A ARDUINO\n")
			print(e)
		finally:
			# self.arduino.close()
			pass