/**
 * Supplier Management Logic
 * Handles real-time filtering of the supplier table.
 */
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById("suppliersearch");
    const tableRows = document.querySelectorAll("#suppliertable tbody tr");

    if (searchInput) {
        searchInput.addEventListener("keyup", function () {
            const filter = this.value.toLowerCase();

            tableRows.forEach(row => {
                const idCell = row.cells[0];
                const nameCell = row.cells[1];

                const matches = idCell.textContent.toLowerCase().includes(filter) ||
                    nameCell.textContent.toLowerCase().includes(filter);

                row.style.display = matches ? "" : "none";
            });
        });
    }
});