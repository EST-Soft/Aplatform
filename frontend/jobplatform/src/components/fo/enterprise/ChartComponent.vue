<template>
    <div>
      <canvas
        ref="barChart"
      />
    </div>
  </template>
  
  <script>
  import { Chart, registerables } from 'chart.js'
  Chart.register(...registerables)
  
  export default {
    data:() => ({
      type: 'bar',
      axis: 'y',
      data: {
        labels: [ '1-56', '1-55', '1-54', '1-53', '1-52' ],
        datasets: [{
        //   label: '# of Votes',
          data: [ 5, 12, 3, 8, 20 ],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(255, 159, 64, 0.2)'
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(255, 159, 64, 1)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          x: {
            beginAtZero: true
          }
        },
        indexAxis: 'y'
      }
    }),
    mounted(){
      this.createChart()
    },
    methods:{
      createChart(){
        new Chart(this.$refs.barChart, {
          type:'bar',
          data:this.data,
          options:this.options
        })
  
      }
    }
  }
  </script>
  