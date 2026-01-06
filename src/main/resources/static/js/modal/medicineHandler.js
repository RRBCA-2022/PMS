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

function openEditMedicineModal(id) {
    fetch(`/medicine/edit/${id}`)
        .then(res => res.json())
        .then(med => {

            document.querySelector('[name="id"]').value = med.id
            document.querySelector('[name="name"]').value = med.name
            document.querySelector('[name="description"]').value = med.description
            document.querySelector('[name="price"]').value = med.price
            document.querySelector('[name="qty"]').value = med.qty
            document.querySelector('[name="qtyUnit"]').value = med.qtyUnit
            document.querySelector('[name="location"]').value = med.location
            document.querySelector('[name="mfgDate"]').value = med.mfgDate
            document.querySelector('[name="expDate"]').value = med.expDate

            // category
            document.getElementById("categoryId").value = med.category.id
            document.getElementById("categoryInput").value = med.category.name

            // title
            document.querySelector("#medicineModal h5").textContent = "Edit Medicine"

            openMedicineModal()
        })
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


// ------------------------------------------

// Close dropdown when clicking outside
document.addEventListener("click", function(event) {
    const selectBox = document.querySelector(".category-select");
    const dropdown = document.getElementById("categoryDropdown");
    if (!selectBox.contains(event.target)) {
        dropdown.style.display = "none";
    }
});

document.querySelector("#medicineModal form")
    .addEventListener("submit", function (e) {
        e.preventDefault()
        saveMedicine()
    })

function saveMedicine() {

    const form = document.querySelector("#medicineModal form")
    const formData = new FormData(form)
    const categoryId = document.getElementById("categoryId").value;

    const data = Object.fromEntries(formData.entries())
    data.qtyUnit = data.qtyUnit.toUpperCase();
    data.category = { id: categoryId };  // <-- IMPORTANT
    delete data["category.id"];          // remove the old key

    console.log(data)

    fetch("/medicine/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(res => res.json())
        .then(saved => {

            console.log(saved);

            // 1️⃣ close add medicine modal
            closeMedicineModal()

            // 2️⃣ add to purchase available products
            appendMedToAvailableList(saved)

        })
}

function appendMedToAvailableList(m) {

    const currencySymbol =
        document.getElementById("grandTotal")?.dataset.currSymbol || ""

    const list = document.getElementById("availableProducts")

    const div = document.createElement("div")
    div.className = "list-group-item d-flex justify-content-between"

    console.log(m);

    div.innerHTML = `
		<div>
            <strong th:text="${m.name}"></strong><br>
            <small th:text="'Cost: ' + ${currencySymbol} + ${m.price}"></small>
        </div>
        <button class="btn btn-sm btn-primary" type="button"
                onclick="addMedicineToPurchase(this)">
            Add
        </button>
	`

    list.prepend(div)
}

