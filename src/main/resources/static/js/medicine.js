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
    console.log("[openEditMedicineModal] Fetching medicine with ID:", id);

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

let activeIndx = -1;

function clearCategoryInput() {
    const input = document.getElementById("categoryInput");
    input.value = "";
    document.getElementById("categoryId").value = "";
    const dropdown = document.getElementById("categoryDropdown");
    dropdown.style.display = "block";
    activeIndx = -1;
    filterCategoryDropdown();
}



function selectCategory(li) {

    const id = li.getAttribute("data-id");
    const name = li.getAttribute("data-name");

    document.getElementById("categoryId").value = id;
    document.getElementById("categoryInput").value = name;

    document.getElementById("categoryDropdown").style.display = "none";
    activeIndx = -1;
}

function filterCategoryDropdown() {
    const inputEl = document.getElementById("categoryInput");
    const input = inputEl.value.toLowerCase();
    const items = document.querySelectorAll("#categoryDropdown li");
    let anyVisible = false;

    items.forEach((li, index) => {
        const text = li.getAttribute("data-name").toLowerCase();
        if (text.includes(input) || input === "") {
            li.style.display = "block";
            anyVisible = true;
        } else {
            li.style.display = "none";
        }
        li.classList.remove("active");
    });

    // Show dropdown if at least one match OR input is empty
    document.getElementById("categoryDropdown").style.display = anyVisible ? "block" : "none";
    activeIndx = -1;
}

// Handle arrow keys + enter selection
function handleCategoryKey(e) {
    const items = Array.from(document.querySelectorAll("#categoryDropdown li")).filter(li => li.style.display !== "none");
    if (!items.length) return;

    if (e.key === "ArrowDown") {
        e.preventDefault();
        activeIndx = (activeIndx + 1) % items.length;
        updateActiveItem(items);
    } else if (e.key === "ArrowUp") {
        e.preventDefault();
        activeIndx = (activeIndx - 1 + items.length) % items.length;
        updateActiveItem(items);
    } else if (e.key === "Enter") {
        e.preventDefault();
        if (activeIndx >= 0 && activeIndx < items.length) {
            selectCategory(items[activeIndx]);
        }
    }
}

function updateActiveItem(items) {
    items.forEach((li, index) => {
        if (index === activeIndx) li.classList.add("active");
        else li.classList.remove("active");
    });
}

// **Clear invalid input on blur**
document.getElementById("categoryInput").addEventListener("blur", () => {
    setTimeout(() => {
        const inputEl = document.getElementById("categoryInput");
        const inputVal = inputEl.value.toLowerCase();
        const items = Array.from(document.querySelectorAll("#categoryDropdown li"));
        const match = items.find(li => li.getAttribute("data-name").toLowerCase() === inputVal);

        if (!match) {
            inputEl.value = "";
            document.getElementById("categoryId").value = "";
        }

        document.getElementById("categoryDropdown").style.display = "none";
    }, 150); // delay ensures click on dropdown item runs first
});


// ------------------------------------------

// Close dropdown when clicking outside
document.addEventListener("click", function(event) {
    const dropdowns = document.querySelectorAll(".custom-select-wrapper");
    dropdowns.forEach(wrapper => {
        const input = wrapper.querySelector(".custom-select-input");
        const dropdown = wrapper.querySelector(".custom-select-dropdown");
        if (!wrapper.contains(event.target) && dropdown) {
            dropdown.style.display = "none";
        }
    });
});

document.addEventListener("submit", function (e) {
    if (e.target && e.target.closest("#medicineModal form")) {
        e.preventDefault();
        saveMedicine();
    }
});

function updateMedicineRow(med) {
    console.log("[updateMedicineRow] Updating row for medicine:", med);

    const row = document.querySelector(`tr[data-id='${med.id}']`);
    if (!row) {
        console.warn("[updateMedicineRow] Row not found for ID", med.id);
        return;
    }

    // Update using querySelector with class names
    row.querySelector(".td-name span").textContent = med.name;
    row.querySelector(".td-manufacturer").textContent = med.manufacturer;
    row.querySelector(".td-desc").textContent = med.description;
    row.querySelector(".td-category").textContent = med.category.name;
    row.querySelector(".td-price").textContent = med.price;
    row.querySelector(".td-qty").textContent = med.qty;
    row.querySelector(".td-qtyUnit").textContent = med.qtyUnit.toUpperCase();
    row.querySelector(".td-location").textContent = med.location;
    row.querySelector(".td-expDate").textContent = med.expDate;
}

/**
 * Quick View Handler for Medicine Details
 * Master Seven, this pulls data directly from the DOM for maximum speed.
 */
function viewMedicineDetails(row) {
    const id = row.dataset.id;
    const name = row.querySelector('.td-name span').innerText;
    const manufacturer = row.querySelector('.td-manufacturer').innerText;
    const category = row.querySelector('.td-category').innerText;
    const price = row.querySelector('.td-price').innerText;
    const qty = row.querySelector('.td-qty span').innerText;
    const status = row.querySelector('.td-status').innerText.trim();

    const detailString = `
📦 Medicine Details
-------------------------
ID: ${id}
Name: ${name}
Manufacturer: ${manufacturer}
Category: ${category}
Price: ${price}
Stock: ${qty}
Current Status: ${status}
    `;

    alert(detailString);
}

function saveMedicine() {

    const form = document.querySelector("#medicineModal form")
    const formData = new FormData(form)
    const categoryId = document.getElementById("categoryId").value;

    const data = Object.fromEntries(formData.entries())
    data.qtyUnit = data.qtyUnit.toUpperCase();
    data.category = { id: categoryId };  // <-- IMPORTANT
    delete data["category.id"];
    const mfg = new Date(data.mfgDate);
    const exp = new Date(data.expDate);

    // 2. Perform the logical check
    if (exp <= mfg) {
        alert("Expiry date must be after manufacturing date!");
        return; // This 'return' is the most important part; it stops the fetch.
    }

    let isEditMode = document.querySelector("#medicineModal h5").textContent === "Edit Medicine";

    fetch("/medicine/save", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    })
        .then(res => res.text())
        .then(text => {
            try {
                const json = JSON.parse(text);

                console.log("[saveMedicine] Server response:", json);

                // close modal
                closeMedicineModal();

                // add or update in available list
                if (!isEditMode)
                    appendMedToAvailableList(json);
                else
                    updateMedicineRow(json);

            } catch (err) {
                console.error("Save Medicine Error :", err);
            }
        });

}

function appendMedToAvailableList(m) {

    const list = document.getElementById("availableProducts")

    if (!list) {
        console.warn("[appendMed] availableProducts not found in DOM.");
        return;
    }

    const currencySymbol = document.getElementById('global-currency-symbol').value;


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

    // attach ID to button
    const button = div.querySelector("button")
    button.dataset.id = m.id   // <-- attach medicine ID

    button.onclick = function () {
        addMedicineToList(this, m.price)
    }

    list.prepend(div)
}

/**
 * Medicine Management Logic
 * Handles real-time search filtering and modal interactions.
 */
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById("medicineSearch");
    const tableRows = document.querySelectorAll("#medicineTable tbody tr");

    // 1. Search Filtering Logic
    if (searchInput) {
        searchInput.addEventListener("keyup", function () {
            const filter = this.value.toLowerCase();

            tableRows.forEach(row => {
                const idCell = row.cells[0];   // ID Column
                const nameCell = row.cells[1]; // Name & Manufacturer Column
                const categoryCell = row.cells[2]; // Category Column

                const matches = idCell.textContent.toLowerCase().includes(filter) ||
                    nameCell.textContent.toLowerCase().includes(filter) || categoryCell.textContent.toLowerCase().includes(filter);

                row.style.display = matches ? "" : "none";
            });
        });
    }
});

