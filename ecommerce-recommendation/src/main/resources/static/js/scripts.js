// Navigation
const homeSection = document.getElementById('homeSection');
const allProductsSection = document.getElementById('allProductsSection');
const recommendationsSection = document.getElementById('recommendationsSection');
const homeLink = document.getElementById('homeLink');
const allProductsLink = document.getElementById('allProductsLink');

homeLink.addEventListener('click', (e) => {
    e.preventDefault();
    homeSection.style.display = '';
    allProductsSection.style.display = 'none';
    recommendationsSection.style.display = 'none';
    homeLink.classList.add('active');
    allProductsLink.classList.remove('active');
});

allProductsLink.addEventListener('click', async (e) => {
    e.preventDefault();
    homeSection.style.display = 'none';
    allProductsSection.style.display = '';
    recommendationsSection.style.display = 'none';
    allProductsLink.classList.add('active');
    homeLink.classList.remove('active');
    await loadAllProducts();
});

// Modal for recommendations
const recommendModal = document.getElementById('recommendModal');
document.getElementById('openRecommendModalBtn').onclick = () => {
    recommendModal.style.display = 'flex';
    document.getElementById('modalUserId').value = '';
};
document.getElementById('closeModalBtn').onclick = () => {
    recommendModal.style.display = 'none';
};
window.onclick = (event) => {
    if (event.target === recommendModal) recommendModal.style.display = 'none';
};

// Get Recommendations
document.getElementById('modalGetRecommendationsBtn').onclick = async () => {
    const userId = document.getElementById('modalUserId').value;
    if (!userId) {
        alert('Please enter a valid User ID.');
        return;
    }
    recommendModal.style.display = 'none';
    await loadRecommendations(userId);
};

document.getElementById('closeRecommendationsBtn').onclick = () => {
    recommendationsSection.style.display = 'none';
    homeSection.style.display = '';
    allProductsSection.style.display = 'none';
};

// Helper to deduplicate products by name
function deduplicateProducts(products) {
    const seen = new Set();
    return products.filter(p => {
        if (!p.name) return false;
        if (seen.has(p.name)) return false;
        seen.add(p.name);
        return true;
    });
}

// All products category filter ONLY
document.getElementById('categoryFilter').addEventListener('change', filterAllProducts);

async function loadAllProducts() {
    const res = await fetch('/api/recommendations/all');
    let products = await res.json();
    products = deduplicateProducts(products);
    window.allProductsCache = products;
    filterAllProducts(); // Always apply filter after loading
}

function filterAllProducts() {
    const category = document.getElementById('categoryFilter').value.trim();
    let filtered = (window.allProductsCache || []).filter(
        p => p.name && p.category
    );
    if (category && category !== "") {
        filtered = filtered.filter(
            p => p.category.trim() === category
        );
    }
    // Deduplicate again just in case
    filtered = deduplicateProducts(filtered);
    renderProductList(filtered, document.getElementById('allProductsList'));
}

// Home page search functionality
document.getElementById('homeSearchBtn').addEventListener('click', homeSearchProducts);

async function homeSearchProducts() {
    const query = document.getElementById('homeSearchBar').value.trim().toLowerCase();
    if (!query) {
        document.getElementById('homeSearchResults').innerHTML = '';
        return;
    }
    // Fetch all products if not already loaded
    if (!window.allProductsCache) {
        const res = await fetch('/api/recommendations/all');
        window.allProductsCache = deduplicateProducts(await res.json());
    }
    const results = deduplicateProducts(
        window.allProductsCache.filter(
            p => p.name && p.name.toLowerCase().includes(query)
        )
    );
    renderProductList(results, document.getElementById('homeSearchResults'));
}

// Recommendations
async function loadRecommendations(userId) {
    const recommendationsList = document.getElementById('recommendationsList');
    recommendationsList.innerHTML = '<p>Loading...</p>';
    recommendationsSection.style.display = '';
    homeSection.style.display = 'none';
    allProductsSection.style.display = 'none';

    try {
        const response = await fetch(`/api/recommendations/${userId}`);
        if (!response.ok) throw new Error('Failed to fetch recommendations');
        let recommendations = await response.json();
        recommendations = deduplicateProducts(recommendations);
        renderProductList(recommendations, recommendationsList);
    } catch (error) {
        recommendationsList.innerHTML = `<p style="color:red;">Error fetching recommendations: ${error.message}</p>`;
    }
}
function deduplicateProducts(products) {
    const seen = new Set();
    return products.filter(p => {
        if (!p.name) return false;
        if (seen.has(p.name)) return false;
        seen.add(p.name);
        return true;
    });
}

// Render product cards
function renderProductList(products, container) {
    container.innerHTML = '';
    if (!products.length) {
        container.innerHTML = '<p>No products found.</p>';
        return;
    }
    products.forEach(product => {
        if (!product.name || !product.category) return; // skip invalid
        const card = document.createElement('div');
        card.className = 'product-card';
        card.innerHTML = `
            <img src="${product.image}" alt="${product.name}">
            <p><strong>${product.name}</strong></p>
            <p style="font-size:0.95em;color:#3D405B;">${product.category}</p>
        `;
        container.appendChild(card);
    });
}

// Initial load
homeSection.style.display = '';
allProductsSection.style.display = 'none';
recommendationsSection.style.display = 'none';