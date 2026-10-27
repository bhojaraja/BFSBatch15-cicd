let display = document.getElementById('display');
let errorElement = document.getElementById('error');
let currentInput = '';
let operator = null;
let previousValue = null;
let shouldResetDisplay = false;

function appendNumber(num) {
    clearError();

    if (shouldResetDisplay) {
        currentInput = '';
        shouldResetDisplay = false;
    }

    if (num === '.' && currentInput.includes('.')) {
        return;
    }

    currentInput += num;
    updateDisplay();
}

function appendOperator(op) {
    clearError();

    if (currentInput === '' && previousValue === null) {
        return;
    }

    if (operator !== null && currentInput !== '') {
        calculate();
    }

    previousValue = parseFloat(currentInput) || previousValue;
    operator = op;
    currentInput = '';
    shouldResetDisplay = true;
    updateDisplay();
}

function calculate() {
    if (operator === null || currentInput === '' || previousValue === null) {
        return;
    }

    const operand1 = previousValue;
    const operand2 = parseFloat(currentInput);

    if (isNaN(operand2)) {
        showError('Invalid input');
        return;
    }

    sendCalculationRequest(operand1, operator, operand2);
}

function sendCalculationRequest(operand1, op, operand2) {
    const payload = {
        operand1: operand1,
        operator: op,
        operand2: operand2
    };

    fetch('/calculate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            showError(data.error);
            resetCalculator();
        } else {
            currentInput = data.result.toString();
            operator = null;
            previousValue = null;
            shouldResetDisplay = true;
            updateDisplay();
        }
    })
    .catch(error => {
        showError('Server error: ' + error);
        console.error('Error:', error);
    });
}

function clearDisplay() {
    clearError();
    resetCalculator();
    updateDisplay();
}

function deleteLast() {
    clearError();
    currentInput = currentInput.slice(0, -1);
    updateDisplay();
}

function resetCalculator() {
    currentInput = '';
    operator = null;
    previousValue = null;
    shouldResetDisplay = false;
}

function updateDisplay() {
    if (shouldResetDisplay && currentInput === '') {
        display.value = previousValue !== null ? previousValue : '0';
    } else {
        display.value = currentInput || '0';
    }
}

function showError(message) {
    errorElement.textContent = message;
    errorElement.style.display = 'block';
}

function clearError() {
    errorElement.textContent = '';
    errorElement.style.display = 'none';
}

document.addEventListener('keydown', function(event) {
    clearError();

    if (event.key >= '0' && event.key <= '9') {
        appendNumber(event.key);
    } else if (event.key === '.') {
        appendNumber('.');
    } else if (event.key === '+' || event.key === '-' || event.key === '*' || event.key === '/') {
        appendOperator(event.key);
    } else if (event.key === 'Enter' || event.key === '=') {
        event.preventDefault();
        calculate();
    } else if (event.key === 'Backspace') {
        deleteLast();
    } else if (event.key === 'Escape') {
        clearDisplay();
    } else if (event.key === 'c' || event.key === 'C') {
        clearDisplay();
    }
});

updateDisplay();
