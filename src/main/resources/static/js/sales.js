document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById("salesSearch");
    const tableRows = document.querySelectorAll("table tbody tr");

    if (searchInput) {
        searchInput.addEventListener("keyup", function () {
            const filter = this.value.toLowerCase();
            tableRows.forEach(row => {
                const idCell = row.cells[0];   // ID Column
                const sellerCell = row.cells[4];   // Seller Column

                const matches = idCell.textContent.toLowerCase().includes(filter) ||
                    sellerCell.textContent.toLowerCase().includes(filter);
                row.style.display = matches ? "" : "none";
            });
        });
    }
});