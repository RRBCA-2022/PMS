/* ===============================
   PURCHASE MODAL
=============================== */

let listItems = [];

function resetModal() {
    // Reset items array
    listItems = [];
    renderListItems();
}

function toggleModal(modalId, backdropId, isOpenReq = true) {
    if (isOpenReq) openModal(modalId, backdropId);
    else closeModal(modalId, backdropId);
    resetModal();
}

function openPurchaseModal() {
    toggleModal("purchaseModal", "purchaseBackdrop", true);
}

function closePurchaseModal() {
    toggleModal("purchaseModal", "purchaseBackdrop", false);
}

function openSaleModal() {
    toggleModal("saleModal", "saleBackdrop", true);
}

function closeSaleModal() {
    toggleModal("saleModal", "saleBackdrop", false);
}

// purchase only
let supplierFocusIndex = -1;

function clearSupplierInput() {
    const input = document.getElementById('supplierInput');
    const hidden = document.getElementById('supplierId');
    input.value = '';
    hidden.value = '';

    const dropdown = document.getElementById('supplierDropdown');
    dropdown.style.display = "block";  // show dropdown

    supplierFocusIndex = -1;
    filterSupplierDropdown()
}

function selectSupplier(li) {
    const id = li.getAttribute("data-id");
    const name = li.getAttribute("data-name");

    document.getElementById("supplierId").value = id;
    document.getElementById("supplierInput").value = name;

    document.getElementById("supplierDropdown").style.display = "none";
    supplierFocusIndex = -1; // reset arrow key navigation
}

function filterSupplierDropdown() {
    const input = document.getElementById('supplierInput');
    const filter = input.value.toLowerCase();
    const dropdown = document.getElementById('supplierDropdown');
    const items = dropdown.getElementsByTagName('li');

    for (let i = 0; i < items.length; i++) {
        const txt = items[i].textContent.toLowerCase();
        items[i].style.display = txt.includes(filter) ? 'block' : 'none';
    }
}

function handleSupplierKey(e) {
    const dropdown = document.getElementById('supplierDropdown');
    const items = dropdown.getElementsByTagName('li');

    if (!items.length) return;

    if (e.key === "ArrowDown") {
        e.preventDefault();
        supplierFocusIndex++;
        if (supplierFocusIndex >= items.length) supplierFocusIndex = 0;
        setActiveSupplierItem(items);
    } else if (e.key === "ArrowUp") {
        e.preventDefault();
        supplierFocusIndex--;
        if (supplierFocusIndex < 0) supplierFocusIndex = items.length - 1;
        setActiveSupplierItem(items);
    } else if (e.key === "Enter") {
        e.preventDefault();
        if (supplierFocusIndex >= 0 && supplierFocusIndex < items.length) {
            selectSupplier(items[supplierFocusIndex]);
        }
    } else {
        // reset index on any other key so arrow selection starts fresh
        supplierFocusIndex = -1;
    }
}

function setActiveSupplierItem(items) {
    for (let i = 0; i < items.length; i++) {
        items[i].classList.remove('active');
    }
    if (supplierFocusIndex >= 0 && supplierFocusIndex < items.length) {
        items[supplierFocusIndex].classList.add('active');
        // scroll into view if the item is outside dropdown
        items[supplierFocusIndex].scrollIntoView({ block: 'nearest' });
    }
}

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


/* ---------- Add / Render Medicines ---------- */
function addMedicineToList(button, price) {
    const itemEl = button.closest(".list-group-item")
    const name = itemEl.querySelector("strong").innerText
    const id = button.dataset.id  // now you get the ID
    const priceNum = price

    const existing = listItems.find(i => i.id === id)
    if (existing) {
        existing.qty += 1
        existing.total = existing.qty * existing.price
    } else {
        listItems.push({ id, name, price: priceNum, qty: 1, total: priceNum })
    }

    renderListItems()
}

function renderListItems() {
    const list = document.getElementById("itemsList");
    list.innerHTML = "";

    let grandTotal = 0;
    const grandTotalEl = document.getElementById("grandTotal");
    const currency = grandTotalEl.dataset.currSymbol || "$";

    listItems.forEach((item, index) => {
        grandTotal += item.total;

        const div = document.createElement("div");
        div.className = "list-group-item d-flex justify-content-between align-items-center";

        div.innerHTML = `
            <div>
                <strong>${item.name}</strong><br>
                <small>${currency} ${item.price.toLocaleString(undefined, {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        })} each</small>
            </div>
            <div class="d-flex align-items-center gap-2">
                <button type="button" class="btn btn-sm btn-light" onclick="decreaseQty(${index})">−</button>
                <input type="number" min="1" max="9999" class="qty-input text-center" value="${item.qty}" 
                        onchange="updateQty(${index}, this.value)">
                <button type="button" class="btn btn-sm btn-light" onclick="increaseQty(${index})">+</button>
                <strong>${currency} ${item.total.toLocaleString(undefined, {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        })}</strong>

                <button type="button"
                        class="btn btn-sm btn-link text-danger remove-item"
                        onclick="removeItem(${index})">
                    &times;
                </button>
                
                <input type="hidden" name="items[${index}].id" value="${item.id}">
                <input type="hidden" name="items[${index}].name" value="${item.name}">
                <input type="hidden" name="items[${index}].price" value="${item.price}">
                <input type="hidden" name="items[${index}].qty" value="${item.qty}">
            </div>
        `;

        list.appendChild(div);
    });

    grandTotalEl.innerText = currency + ' ' + grandTotal.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

function removeItem(index) {
    listItems.splice(index, 1);
    renderListItems();
}

/* ---------- Quantity Control ---------- */
// New helper function to handle manual input
function updateQty(index, value) {
    let qty = parseInt(value);

    // Reset invalid or out-of-range input
    if (isNaN(qty) || qty < 1) qty = 1;
    if (qty > 9999) qty = 9999; // clamp max value

    listItems[index].qty = qty;
    listItems[index].total = listItems[index].price * qty;

    renderListItems();
}

// Existing helpers for + and − buttons
function increaseQty(index) {
    updateQty(index, listItems[index].qty + 1);
}

function decreaseQty(index) {
    if (listItems[index].qty > 1) {
        updateQty(index, listItems[index].qty - 1);
    }
}

/* ---------- Filter Available Medicines ---------- */
function filterMedicines() {
    const search = document.getElementById("medicineSearch").value.toLowerCase();

    // Select all list-group-item under availableProducts
    const items = document.querySelectorAll("#availableProducts .list-group-item");

    items.forEach(item => {
        const nameEl = item.querySelector("strong");
        if (!nameEl) return; // skip if no strong tag

        const name = nameEl.innerText.toLowerCase().trim().replace(/\s+/g, ' ');

        // Show or hide item based on search
        if (name.includes(search)) {
            item.classList.remove("d-none");  // show
        } else {
            item.classList.add("d-none");     // hide
        }
    });
}

document.getElementById("medicineSearch").addEventListener("input", filterMedicines);
