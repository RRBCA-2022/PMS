/* ===============================
   PURCHASE MODAL
=============================== */

let purchaseItems = [];

function openPurchaseModal() {
    openModal("purchaseModal", "purchaseBackdrop");

    // Reset items array
    purchaseItems = [];
    renderPurchaseItems();
}

function closePurchaseModal() {
    closeModal("purchaseModal", "purchaseBackdrop");

    // Reset items array
    purchaseItems = [];
    renderPurchaseItems();
}

/* ---------- Add / Render Medicines ---------- */
function addMedicineToPurchase(button, p) {
    const itemEl = button.closest(".list-group-item");
    const name = itemEl.querySelector("strong").innerText;
    const price = p;

    console.log(p)

    const existing = purchaseItems.find(i => i.name === name);
    if (existing) {
        existing.qty += 1;
        existing.total = existing.qty * existing.price;
    } else {
        purchaseItems.push({ name, price, qty: 1, total: price });
    }

    renderPurchaseItems();
}

function renderPurchaseItems() {
    const list = document.getElementById("purchaseItemsList");
    list.innerHTML = "";

    let grandTotal = 0;
    const grandTotalEl = document.getElementById("grandTotal");
    const currency = grandTotalEl.dataset.currSymbol || "$";

    purchaseItems.forEach((item, index) => {
        grandTotal += item.total;

        const div = document.createElement("div");
        div.className = "list-group-item d-flex justify-content-between align-items-center";

        div.innerHTML = `
            <div>
                <strong>${item.name}</strong><br>
                <small>${currency} ${item.price.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })} each</small>
            </div>
            <div class="d-flex align-items-center gap-2">
                <button type="button" class="btn btn-sm btn-light" onclick="decreaseQty(${index})">−</button>
                <input type="number" min="1" max="9999" class="qty-input text-center" value="${item.qty}" 
                        onchange="updateQty(${index}, this.value)">
                <button type="button" class="btn btn-sm btn-light" onclick="increaseQty(${index})">+</button>
                <strong>${currency} ${item.total.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</strong>
                <input type="hidden" name="items[${index}].name" value="${item.name}">
                <input type="hidden" name="items[${index}].price" value="${item.price}">
                <input type="hidden" name="items[${index}].qty" value="${item.qty}">
            </div>
        `;

        list.appendChild(div);
    });

    grandTotalEl.innerText = currency + ' ' + grandTotal.toLocaleString(undefined, { minimumFractionDigits: 2, maximumFractionDigits: 2 });
}

/* ---------- Quantity Control ---------- */
// New helper function to handle manual input
function updateQty(index, value) {
    let qty = parseInt(value);

    // Reset invalid or out-of-range input
    if (isNaN(qty) || qty < 1) qty = 1;
    if (qty > 9999) qty = 9999; // clamp max value

    purchaseItems[index].qty = qty;
    purchaseItems[index].total = purchaseItems[index].price * qty;

    renderPurchaseItems();
}

// Existing helpers for + and − buttons
function increaseQty(index) {
    updateQty(index, purchaseItems[index].qty + 1);
}

function decreaseQty(index) {
    if (purchaseItems[index].qty > 1) {
        updateQty(index, purchaseItems[index].qty - 1);
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
