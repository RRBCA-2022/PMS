function toggleCategoryDropdown() {
    const dropdown = document.getElementById("categoryDropdown");
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";
}

function selectCategory(el) {
    document.getElementById("categoryId").value = el.dataset.id;
    document.getElementById("categoryInput").value = el.dataset.name;
    document.getElementById("categoryDropdown").style.display = "none";
}

// Close dropdown when clicking outside
document.addEventListener("click", function (e) {
    const select = document.querySelector(".category-select");
    const dropdown = document.getElementById("categoryDropdown");

    if (!select.contains(e.target) && !dropdown.contains(e.target)) {
        dropdown.style.display = "none";
    }
});
