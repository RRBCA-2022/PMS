// /js/modules/ChartModule.js
class ChartModule {
    constructor(containerId) {
        this.container = document.getElementById(containerId);
        this.canvas = this.container.querySelector('.chart-canvas');
        this.label = this.container.querySelector('.period-label');
        this.apiUrl = this.container.dataset.url;
        this.chart = null;

        this.init();
    }

    init() {
        // Bind dropdown clicks
        this.container.querySelectorAll('[data-period]').forEach(item => {
            item.addEventListener('click', (e) => {
                e.preventDefault();
                const period = e.target.dataset.period;
                this.label.innerText = e.target.innerText;
                this.fetchData(period);
            });
        });

        // Initial Load
        this.fetchData('daily');
    }

    async fetchData(period) {
        try {
            const response = await fetch(`${this.apiUrl}?period=${period}`);
            const data = await response.json();
            this.render(data.labels, data.values);
        } catch (err) {
            console.error("Chart Fetch Error:", err);
        }
    }

    render(labels, values) {
        if (this.chart) {
            this.chart.destroy();
        }

        this.chart = new Chart(this.canvas.getContext('2d'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Revenue',
                    data: values,
                    borderColor: '#0d6efd',
                    backgroundColor: 'rgba(13, 110, 253, 0.05)',
                    fill: true,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true } }
            }
        });
    }
}

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    new ChartModule('salesTrendComponent');
});