// DOM Elements
const reportCards = document.querySelectorAll('.report-card');
const reportModal = document.getElementById('reportModal');
const generateModal = document.getElementById('generateModal');
const generateReportBtn = document.getElementById('generateReportBtn');
const closeModalButtons = document.querySelectorAll('.close-modal, .btn-close');
const modalTitle = document.getElementById('modalTitle');
const reportDetails = document.getElementById('reportDetails');
const timePeriodSelect = document.getElementById('timePeriod');
const customRangeDiv = document.getElementById('customRange');
const reportChart = document.getElementById('reportChart');

// Chart.js instance
let chartInstance = null;

// Sample data for charts
const chartData = {
    expense: {
        labels: ['Housing', 'Food', 'Transportation', 'Entertainment', 'Utilities', 'Healthcare'],
        datasets: [{
            label: 'Monthly Expenses',
            data: [1200, 600, 450, 300, 250, 200],
            backgroundColor: [
                '#4361ee', '#4cc9f0', '#4895ef', '#3f37c9', '#f72585', '#7209b7'
            ]
        }]
    },
    income: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'Salary',
            data: [4500, 4500, 4500, 4500, 4500, 4800],
            borderColor: '#4361ee',
            backgroundColor: 'rgba(67, 97, 238, 0.1)',
            fill: true,
            tension: 0.4
        }, {
            label: 'Freelance',
            data: [800, 600, 1200, 900, 1100, 1000],
            borderColor: '#4cc9f0',
            backgroundColor: 'rgba(76, 201, 240, 0.1)',
            fill: true,
            tension: 0.4
        }]
    },
    credit: {
        labels: ['Payment History', 'Credit Utilization', 'Credit Age', 'Credit Mix', 'New Credit'],
        datasets: [{
            label: 'Credit Factors',
            data: [35, 25, 20, 15, 5],
            backgroundColor: [
                '#4361ee', '#4cc9f0', '#4895ef', '#3f37c9', '#f72585'
            ]
        }]
    },
    debt: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'Credit Card',
            data: [5000, 4500, 4000, 3500, 3000, 2500],
            borderColor: '#4361ee',
            backgroundColor: 'rgba(67, 97, 238, 0.1)',
            fill: true
        }, {
            label: 'Student Loan',
            data: [25000, 24500, 24000, 23500, 23000, 22500],
            borderColor: '#4cc9f0',
            backgroundColor: 'rgba(76, 201, 240, 0.1)',
            fill: true
        }]
    },
    savings: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'Emergency Fund',
            data: [2000, 2500, 3000, 3500, 4000, 4500],
            borderColor: '#4361ee',
            backgroundColor: 'rgba(67, 97, 238, 0.1)',
            fill: true
        }, {
            label: 'Retirement',
            data: [15000, 16000, 17000, 18000, 19000, 20000],
            borderColor: '#4cc9f0',
            backgroundColor: 'rgba(76, 201, 240, 0.1)',
            fill: true
        }]
    },
    networth: {
        labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
        datasets: [{
            label: 'Assets',
            data: [85000, 86000, 88000, 89000, 92000, 95000],
            borderColor: '#4361ee',
            backgroundColor: 'rgba(67, 97, 238, 0.1)',
            fill: true
        }, {
            label: 'Liabilities',
            data: [35000, 34000, 32000, 31000, 30000, 29000],
            borderColor: '#4cc9f0',
            backgroundColor: 'rgba(76, 201, 240, 0.1)',
            fill: true
        }, {
            label: 'Net Worth',
            data: [50000, 52000, 56000, 58000, 62000, 66000],
            borderColor: '#4CAF50',
            backgroundColor: 'rgba(76, 175, 80, 0.1)',
            fill: true
        }]
    }
};

// Report details content
const reportContent = {
    expense: `
        <h4>Expense Breakdown</h4>
        <p>Your total spending for May 2023 was <strong>$3,000</strong>, which is 5% lower than April.</p>
        <ul>
            <li>Housing: $1,200 (40%)</li>
            <li>Food: $600 (20%)</li>
            <li>Transportation: $450 (15%)</li>
            <li>Entertainment: $300 (10%)</li>
            <li>Utilities: $250 (8%)</li>
            <li>Healthcare: $200 (7%)</li>
        </ul>
        <p>You've stayed within your budget for all categories except Entertainment, which was 15% over budget.</p>
    `,
    income: `
        <h4>Income Analysis</h4>
        <p>Your total income for the past 6 months was <strong>$32,200</strong>, with an average of $5,367 per month.</p>
        <p>In June, you received a salary increase of $300, bringing your monthly salary to $4,800.</p>
        <p>Your freelance income has been variable, with the highest month being March at $1,200 and the lowest in February at $600.</p>
        <p>Overall, your income has shown a positive trend, increasing by 12% compared to the previous 6-month period.</p>
    `,
    credit: `
        <h4>Credit Score: 745 (Good)</h4>
        <p>Your credit score has improved by 15 points since last quarter.</p>
        <h4>Key Factors:</h4>
        <ul>
            <li><strong>Payment History (35%):</strong> Excellent - No late payments in the last 7 years</li>
            <li><strong>Credit Utilization (25%):</strong> Good - You're using 28% of your available credit</li>
            <li><strong>Credit Age (20%):</strong> Average - Your average account age is 4 years</li>
            <li><strong>Credit Mix (15%):</strong> Good - You have 3 types of credit accounts</li>
            <li><strong>New Credit (5%):</strong> Good - You've opened 1 new account in the last year</li>
        </ul>
        <p>To improve your score further, consider reducing your credit utilization below 25%.</p>
    `,
    debt: `
        <h4>Debt Overview</h4>
        <p>Your total debt is <strong>$25,000</strong>, which has decreased by 12% since January.</p>
        <h4>Debt Breakdown:</h4>
        <ul>
            <li><strong>Credit Card Debt:</strong> $2,500 at 18.9% APR</li>
            <li><strong>Student Loan:</strong> $22,500 at 4.5% APR</li>
        </ul>
        <p>You're making excellent progress on paying down your credit card debt, having reduced it by 50% in 6 months.</p>
        <p>At your current payment rate, you'll be credit card debt-free in 5 months and student loan debt-free in 8 years.</p>
        <p>Your debt-to-income ratio is 28%, which is within the recommended range.</p>
    `,
    savings: `
        <h4>Savings Progress</h4>
        <p>Your total savings across all accounts is <strong>$24,500</strong>, which represents a 20% increase since January.</p>
        <h4>Savings Breakdown:</h4>
        <ul>
            <li><strong>Emergency Fund:</strong> $4,500 (82% of your $5,500 goal)</li>
            <li><strong>Retirement Fund:</strong> $20,000 (25% of your $80,000 goal)</li>
        </ul>
        <p>You're contributing $500 monthly to your emergency fund and $1,000 monthly to retirement.</p>
        <p>At this rate, you'll reach your emergency fund goal in 2 months and your retirement goal in 5 years.</p>
        <p>Your savings rate is 18% of your income, which is above the recommended 15%.</p>
    `,
    networth: `
        <h4>Net Worth: $66,000</h4>
        <p>Your net worth has increased by 32% in the last 6 months.</p>
        <h4>Assets: $95,000</h4>
        <ul>
            <li>Cash Accounts: $15,000</li>
            <li>Investment Accounts: $40,000</li>
            <li>Retirement Accounts: $20,000</li>
            <li>Real Estate: $20,000 (estimated)</li>
        </ul>
        <h4>Liabilities: $29,000</h4>
        <ul>
            <li>Credit Card Debt: $2,500</li>
            <li>Student Loans: $22,500</li>
            <li>Other Debts: $4,000</li>
        </ul>
        <p>Your assets have grown by 12% while your liabilities have decreased by 17% since January.</p>
        <p>Your positive cash flow and debt reduction strategy are effectively increasing your net worth.</p>
    `
};

// Chart configuration based on report type
function getChartConfig(type) {
    const commonOptions = {
        responsive: true,
        maintainAspectRatio: false
    };
    
    switch(type) {
        case 'expense':
            return {
                type: 'doughnut',
                data: chartData.expense,
                options: {
                    ...commonOptions,
                    plugins: {
                        legend: {
                            position: 'right'
                        }
                    }
                }
            };
        case 'income':
            return {
                type: 'line',
                data: chartData.income,
                options: {
                    ...commonOptions,
                    scales: {
                        y: {
                            beginAtZero: false,
                            title: {
                                display: true,
                                text: 'Amount ($)'
                            }
                        }
                    }
                }
            };
        case 'credit':
            return {
                type: 'pie',
                data: chartData.credit,
                options: {
                    ...commonOptions,
                    plugins: {
                        legend: {
                            position: 'right'
                        }
                    }
                }
            };
        case 'debt':
            return {
                type: 'line',
                data: chartData.debt,
                options: {
                    ...commonOptions,
                    scales: {
                        y: {
                            beginAtZero: false,
                            title: {
                                display: true,
                                text: 'Amount ($)'
                            }
                        }
                    }
                }
            };
        case 'savings':
            return {
                type: 'line',
                data: chartData.savings,
                options: {
                    ...commonOptions,
                    scales: {
                        y: {
                            beginAtZero: false,
                            title: {
                                display: true,
                                text: 'Amount ($)'
                            }
                        }
                    }
                }
            };
        case 'networth':
            return {
                type: 'line',
                data: chartData.networth,
                options: {
                    ...commonOptions,
                    scales: {
                        y: {
                            beginAtZero: false,
                            title: {
                                display: true,
                                text: 'Amount ($)'
                            }
                        }
                    }
                }
            };
        default:
            return {
                type: 'bar',
                data: { labels: [], datasets: [] },
                options: commonOptions
            };
    }
}

// Event Listeners
reportCards.forEach(card => {
    card.addEventListener('click', () => {
        const reportType = card.getAttribute('data-report');
        openReportModal(reportType);
    });
});

generateReportBtn.addEventListener('click', () => {
    generateModal.style.display = 'flex';
});

closeModalButtons.forEach(button => {
    button.addEventListener('click', () => {
        reportModal.style.display = 'none';
        generateModal.style.display = 'none';
    });
});

timePeriodSelect.addEventListener('change', () => {
    if (timePeriodSelect.value === 'custom') {
        customRangeDiv.style.display = 'block';
    } else {
        customRangeDiv.style.display = 'none';
    }
});

// Functions
function openReportModal(reportType) {
    // Set modal title based on report type
    const titles = {
        expense: 'Monthly Expense Report',
        income: 'Income Trends Analysis',
        credit: 'Credit Score Report',
        debt: 'Debt Analysis',
        savings: 'Savings Progress Report',
        networth: 'Net Worth Statement'
    };
    
    modalTitle.textContent = titles[reportType];
    
    // Set report details content
    reportDetails.innerHTML = reportContent[reportType];
    
    // Destroy previous chart if it exists
    if (chartInstance) {
        chartInstance.destroy();
    }
    
    // Create new chart
    const ctx = reportChart.getContext('2d');
    const config = getChartConfig(reportType);
    chartInstance = new Chart(ctx, config);
    
    // Show the modal
    reportModal.style.display = 'flex';
}

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    // Logo click functionality
    const logo = document.querySelector('.logo');
    if (logo) {
        logo.style.cursor = 'pointer';
        logo.addEventListener('click', function() {
            window.location.href = '/';
        });
    }
});