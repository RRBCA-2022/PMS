/* ===============================
   MEDICINE MODAL
=============================== */

function openMedicineModal() {
    openModal("medicineModal", "medicineBackdrop");

    // Reset custom category dropdown
    const modal = document.getElementById("medicineModal");
    const categoryInput = modal.querySelector("#categoryInput");
    if (categoryInput) categoryInput.value = "";

    const categoryId = modal.querySelector("#categoryId");
    if (categoryId) categoryId.value = "";
}

function closeMedicineModal() {
    closeModal("medicineModal", "medicineBackdrop");

    // Reset custom category dropdown
    const modal = document.getElementById("medicineModal");
    const categoryInput = modal.querySelector("#categoryInput");
    if (categoryInput) categoryInput.value = "";

    const categoryId = modal.querySelector("#categoryId");
    if (categoryId) categoryId.value = "";
}

// ----------------------------------
let activeIndex = -1;

function clearCategoryInput() {
    const input = document.getElementById("categoryInput");
    input.value = "";
    document.getElementById("categoryId").value = "";
    const dropdown = document.getElementById("categoryDropdown");
    dropdown.style.display = "block";
    activeIndex = -1;
    filterCategoryDropdown();
}

function selectCategory(li) {
    const id = li.getAttribute("data-id");
    const name = li.getAttribute("data-name");

    document.getElementById("categoryId").value = id;
    document.getElementById("categoryInput").value = name;

    document.getElementById("categoryDropdown").style.display = "none";
    activeIndex = -1;
}

function filterCategoryDropdown() {
    const input = document.getElementById("categoryInput").value.toLowerCase();
    const items = document.querySelectorAll("#categoryDropdown li");
    let anyVisible = false;

    items.forEach((li, index) => {
        const text = li.getAttribute("data-name").toLowerCase();
        if (text.includes(input)) {
            li.style.display = "block";
            anyVisible = true;
        } else {
            li.style.display = "none";
        }
        li.classList.remove("active"); // remove previous active
    });

    document.getElementById("categoryDropdown").style.display = anyVisible ? "block" : "none";
    activeIndex = -1;
}

function handleCategoryKey(e) {
    const items = Array.from(document.querySelectorAll("#categoryDropdown li")).filter(li => li.style.display !== "none");
    if (!items.length) return;

    if (e.key === "ArrowDown") {
        e.preventDefault();
        activeIndex = (activeIndex + 1) % items.length;
        updateActiveItem(items);
    } else if (e.key === "ArrowUp") {
        e.preventDefault();
        activeIndex = (activeIndex - 1 + items.length) % items.length;
        updateActiveItem(items);
    } else if (e.key === "Enter") {
        e.preventDefault();
        if (activeIndex >= 0 && activeIndex < items.length) {
            selectCategory(items[activeIndex]);
        }
    }
}

function updateActiveItem(items) {
    items.forEach((li, index) => {
        if (index === activeIndex) li.classList.add("active");
        else li.classList.remove("active");
    });
}

// Close dropdown when clicking outside
document.addEventListener("click", function(event) {
    const selectBox = document.querySelector(".category-select");
    const dropdown = document.getElementById("categoryDropdown");
    if (!selectBox.contains(event.target)) {
        dropdown.style.display = "none";
    }
});
