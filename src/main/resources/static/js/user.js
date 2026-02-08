/**
 * User Management Logic
 * Handles real-time filtering of the user table.
 */
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById("usersearch");
    const tableRows = document.querySelectorAll("#usertable tbody tr");

    if (searchInput) {
        searchInput.addEventListener("keyup", function () {
            const filter = this.value.toLowerCase();

            tableRows.forEach(row => {
                const nameCell = row.cells[1];
                const usernameCell = row.cells[3];

                const matches = nameCell.textContent.toLowerCase().includes(filter) ||
                    usernameCell.textContent.toLowerCase().includes(filter);

                row.style.display = matches ? "" : "none";
            });
        });
    }
});