# DebtCalculator

A simple tool to calculate total costs of e.g. a credit with interest rate.

# Usage syntax
`java -jar DebtCalculator.jar <double: totalDebt> <double: interestRate> <int: durationInMonths>`
<br>
**NOTE**:<br>
* `totalDebt` is always rounded to two after comma dots.
* `interestRate` must be greater than 0, but also smaller than 0 (e.g. for 25% you do 0.25)