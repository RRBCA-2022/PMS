/**
 * Category Management Logic
 * Handles real-time filtering of the category table based on ID or Name.
 */
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById("categorysearch");
    const tableRows = document.querySelectorAll("#categorytable tbody tr");

    if (searchInput) {
        searchInput.addEventListener("keyup", function () {
            const filter = this.value.toLowerCase();

            tableRows.forEach(row => {
                const idCell = row.cells[0];   // ID column
                const nameCell = row.cells[1]; // Name column

                const matches = idCell.textContent.toLowerCase().includes(filter) ||
                    nameCell.textContent.toLowerCase().includes(filter);

                row.style.display = matches ? "" : "none";
            });
        });
    }
});