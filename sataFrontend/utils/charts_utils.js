export function getDefaultCharDataModel() {
  return ({
    labels: [ '00:01', '00:02', '00:03','00:04', '00:05', '00:06','00:07', '00:08', '00:09','00:10', '00:11', '00:12','00:13', '00:14', '00:15','00:16', '00:17', '00:18'], // X AXIS
    datasets: [
    {
        label: 'Sensor Temperatura 1',
        borderColor: 'rgb(79, 218, 132)',
        backgroundColor: '#4fda8449',
        fill: true,
        data: [19,23,20,21,20,19,23,23,25,23,23,24,24,21,22,18,18,19] // Y AXIS
    },
    {
      label: 'Sensor Temperatura 2',
      borderColor: '#f87979',
      backgroundColor: '#f879798c',
      fill: true,
      data: [10,14,14,16,14,15,12,10,15,16,17,13,16,18,17,17,15,17] // Y AXIS
  },
  ]
  })
}

export function getDefaultChartOptions() {
  return ({
    scales: { 
      y: {
        beginAtZero: true,
        suggestedMax:40,
        suggestedMin:0
      }
    }
  })
}