$(document).ready(function() {
	
	
	var ctx = document.getElementById('myChart').getContext('2d');
	var myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: months,
			datasets: [{
				label: '매출액',
				backgroundColor: 'rgba(54, 162, 235, 0.5)',
				borderColor: 'rgba(54, 162, 235, 1)',
				borderWidth: 1,
				data: sales
			},
			{
				label: '목표 매출액',
				data: targetsales,
				type: 'line',
				borderColor: 'rgba(255, 51, 0, 1)',
				borderWidth: 2,
				pointBackgroundColor: 'rgba(255, 51, 0, 1)',
				pointRadius: 5,
				fill: false
			}]
		},
		options: {
			responsive: true,
			scales: {
				xAxes: [{
					stacked: true
				}],
				yAxes: [{
					stacked: true
				}]
			}
		}
	});

	var ctx2 = document.getElementById('lineChart').getContext('2d');
	var lineChart = new Chart(ctx2, {
		type: 'line',
		data: {
			labels: months,
			datasets: [
				{
					label: '가입자 수',
					data: subscribers,
					borderColor: 'rgba(75, 192, 192, 1)',
					borderWidth: 2,
					pointBackgroundColor: 'rgba(75, 192, 192, 1)',
					pointRadius: 5,
					fill: false
				},
				{
					label: '매입량',
					data: reqcars,
					type: 'bar',
					borderColor: 'rgba(255, 99, 132, 1)',
					backgroundColor: 'rgba(255, 99, 132, 0.5)',
					borderWidth: 1
				},
				{
					label: '판매량',
					data: purchases,
					type: 'bar',
					borderColor: 'rgba(49, 235, 50, 1)',
					backgroundColor: 'rgba(49, 235, 50, 0.5)',
					borderWidth: 1
				}]
		},
		options: {
			responsive: true,
			title: {
				display: true,
				text: '홈페이지 가입자 수',
				fontSize: 18
			},
			scales: {
				yAxes: [{
					ticks: {
						stepSize: 1,
						beginAtZero: true
					}
				}]
			}
		}
	});

});
