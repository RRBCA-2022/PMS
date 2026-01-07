/* ===============================
   MEDICINE MODAL
=============================== */

function openMedicineModal() {
    lockModal("purchaseModal");
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
        .then(res => res.text())  // get raw text
        .then(text => {
            try {
                const med = JSON.parse(text); // parse safely

                openMedicineModal()

                document.getElementById("id").value = med.id
                document.getElementById("name").value = med.name
                document.getElementById("description").value = med.description
                document.getElementById("price").value = med.price
                document.getElementById("qty").value = med.qty
                document.getElementById("qtyUnit").value = med.qtyUnit.toLowerCase()
                document.getElementById("location").value = med.location
                document.getElementById("manufacturer").value = med.manufacturer
                document.getElementById("mfgDate").value = med.mfgDate
                document.getElementById("expDate").value = med.expDate

                // category
                document.getElementById("categoryId").value = med.category.id
                document.getElementById("categoryInput").value = med.category.name

                // title
                document.querySelector("#medicineModal h5").textContent = "Edit Medicine"

            } catch (err) {
                console.error("[Edit Med] Invalid JSON from server:", err);
            }
        });
}

function closeMedicineModal() {
    closeModal("medicineModal", "medicineBackdrop");
    unlockModal("purchaseModal");

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

function updateMedicineRow(med) {
    const row = document.querySelector(`tr[data-id='${med.id}']`);
    if (!row) {
        console.warn("[updateMedicineRow] Row not found for ID", med.id);
        return;
    }

    // Update using querySelector with class names
    row.querySelector(".td-name").textContent = med.name;
    row.querySelector(".td-desc").textContent = med.description;
    row.querySelector(".td-category").textContent = med.category.name;
    row.querySelector(".td-price").textContent = med.price;
    row.querySelector(".td-qty").textContent = med.qty;
    row.querySelector(".td-qtyUnit").textContent = med.qtyUnit.toUpperCase();
    row.querySelector(".td-location").textContent = med.location;
    row.querySelector(".td-manufacturer").textContent = med.manufacturer;
    row.querySelector(".td-mfgDate").textContent = med.mfgDate;
    row.querySelector(".td-expDate").textContent = med.expDate;
}

function saveMedicine() {

    const form = document.querySelector("#medicineModal form")
    const formData = new FormData(form)
    const categoryId = document.getElementById("categoryId").value;

    const data = Object.fromEntries(formData.entries())
    data.qtyUnit = data.qtyUnit.toUpperCase();
    data.category = { id: categoryId };  // <-- IMPORTANT
    delete data["category.id"];

    let isEditMode = document.querySelector("#medicineModal h5").textContent === "Edit Medicine";
    console.log(isEditMode);

    fetch("/medicine/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(text => {
            try {
                const json = JSON.parse(text);
                // close add medicine modal
                closeMedicineModal();

                // add to purchase available products
                if (!isEditMode)
                    appendMedToAvailableList(json);
                else
                    updateMedicineRow(json);
            } catch (err) {
                console.error("Invalid JSON from server:", err);
            }
        });
}

function appendMedToAvailableList(m) {

    const list = document.getElementById("availableProducts")

    if (!list) {
        console.warn("[appendMed] availableProducts not found in DOM. The UI was probably opened in edit mode.");
        return;
    }

    const currencySymbol =
        document.getElementById("grandTotal")?.dataset.currSymbol || ""


    const div = document.createElement("div")
    div.className = "list-group-item d-flex justify-content-between"

    div.innerHTML = `
		<div>
			<strong>${m.name}</strong><br>
			<small>Cost: ${currencySymbol} ${m.price}</small>
		</div>
		<button class="btn btn-sm btn-primary" type="button">
			Add
		</button>
	`

    // attach handler safely
    div.querySelector("button").onclick = function () {
        addMedicineToPurchase(this, m.price)
    }

    list.prepend(div)
}

