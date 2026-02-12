/**
 * Sales Trend Chart Handler for Pharmacy Management System
 */
let salesChart;

function initSalesChart(initialData) {
    const ctx = document.getElementById('salesTrendChart');
    if (!ctx) return;

    renderChart(initialData.labels, initialData.values);
}

function renderChart(labels, values) {
    const ctx = document.getElementById('salesTrendChart').getContext('2d');


    // Destroy previous instance to prevent memory leaks and hover glitches
    if (salesChart) {
        salesChart.destroy();
    }

    salesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Revenue (Rs.)',
                data: values,
                borderColor: '#0d6efd',
                backgroundColor: 'rgba(13, 110, 253, 0.05)',
                fill: true,
                tension: 0.3,
                pointRadius: 4,
                pointBackgroundColor: '#0d6efd'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: function(value) { return 'Rs. ' + value; }
                    }
                }
            },
            plugins: {
                legend: { display: false } // Hiding legend for a cleaner look
            }
        }
    });
}

function updateChart(period) {
    // Update the UI label
    const labelMap = {
        'daily': 'Daily Sales',
        'weekly': 'Weekly Sales',
        'monthly': 'Monthly Sales'
    };

    document.getElementById('periodLabel').innerText = labelMap[period] || 'Sales Trend';

    // Fetch data from your ReportController @GetMapping("/trend")
    fetch('/report/trend?period=' + period)
        .then(res => {
            if (!res.ok) throw new Error('Network response was not ok');
            return res.json();
        })
        .then(data => {
            renderChart(data.labels, data.values);
        })
        .catch(err => console.error("Error fetching trend data:", err));
}