document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById("salesSearch");
    const tableRows = document.querySelectorAll("table tbody tr");

    if (searchInput) {
        searchInput.addEventListener("keyup", function () {
            const filter = this.value.toLowerCase();
            tableRows.forEach(row => {
                // Filters by ID (col 0) or Seller Name (col 4)
                const matches = row.cells[0].textContent.toLowerCase().includes(filter) ||
                    row.cells[4].textContent.toLowerCase().includes(filter);
                row.style.display = matches ? "" : "none";
            });
        });
    }
});