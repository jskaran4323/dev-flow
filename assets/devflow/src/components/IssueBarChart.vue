<!-- src/components/IssueBarChart.vue -->
<template>
    <Bar :data="chartData" :options="chartOptions" />
  </template>
  
  <script setup lang="ts">
  import { Bar } from 'vue-chartjs'
  import {
    Chart as ChartJS,
    Title,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale
  } from 'chart.js'
  
  ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
  
  const props = defineProps<{
    labels: string[]
    data: number[]
  }>()
  
  const chartData = {
    labels: props.labels,
    datasets: [
      {
        label: 'Issues per Project',
        backgroundColor: '#58a6ff',
        data: props.data
      }
    ]
  }
  
  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        labels: {
          color: '#c9d1d9'
        }
      }
    },
    scales: {
      x: {
        ticks: { color: '#c9d1d9' },
        grid: { color: '#30363d' }
      },
      y: {
        ticks: { color: '#c9d1d9' },
        grid: { color: '#30363d' }
      }
    }
  }
  </script>
  