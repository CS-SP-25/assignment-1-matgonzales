public class SalesTaxCalculator {
    
    // Abstract class representing a state
    public abstract static class State {
        private String name;
        protected SalesTaxBehavior salesTaxBehavior;
        
        // Accessor
        public String getName() {
            return name;
        }
        
        // Mutator
        protected void setName(String name) {
            this.name = name;
        }
        
        // Show tax using the SalesTaxBehavior
        public double calculateTax(double value) {
            return salesTaxBehavior.compute(value);
        }

        // Dynamically set the sales tax behavior
        public void setSalesTaxBehavior(SalesTaxBehavior salesTaxBehavior) {
            this.salesTaxBehavior = salesTaxBehavior;
        }
    }
    
    // Interface for sales tax computation
    public interface SalesTaxBehavior {
        double compute(double value);
    }
    
    // No sales tax
    public static class NoTax implements SalesTaxBehavior {
        public double compute(double value) {
            return 0.00;
        }
    }
    
    // Indiana sales tax
    public static class IndianaTax implements SalesTaxBehavior {
        public double compute(double value) {
            return value * 0.07;
        }
    }

    // Hawaii sales tax
    public static class HawaiiTax implements SalesTaxBehavior {
        public double compute(double value) {
            return value * 0.045;
        }
    }
    
    // Indiana state class
    public static class Indiana extends State {
        public Indiana() {
            salesTaxBehavior = new IndianaTax();
            setName("Indiana");
        }
    }
    
    // Alaska state class
    public static class Alaska extends State {
        public Alaska() {
            salesTaxBehavior = new NoTax();
            setName("Alaska");
        }
    }

    // Hawaii state class
    public static class Hawaii extends State {
        public Hawaii() {
            salesTaxBehavior = new HawaiiTax();
            setName("Hawaii");
        }
    }
    
    public static void main(String[] args) {
        // Ensure correct number of arguments
        if (args.length != 2) {
            System.out.println("Usage: java SalesTaxCalculator <State> <SaleAmount>");
            return;
        }
        
        String stateName = args[0];
        double saleAmount;
        
        try {
            saleAmount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Sale amount must be a valid number.");
            return;
        }
        
        State state;
        
        // Determine state object based on input
        if ("Indiana".equalsIgnoreCase(stateName)) {
            state = new Indiana();
            state.setSalesTaxBehavior(new IndianaTax());
        } else if ("Alaska".equalsIgnoreCase(stateName)) {
            state = new Alaska();
            state.setSalesTaxBehavior(new NoTax());
        } else if ("Hawaii".equalsIgnoreCase(stateName)) {
            state = new Hawaii();
            state.setSalesTaxBehavior(new HawaiiTax());
        } else {
            System.out.println("Error: Unsupported state. Allowed states are: Indiana, Alaska, and Hawaii.");
            return;
        }
        
        double tax = state.calculateTax(saleAmount);
        
        // Display the result formatted to two decimal places
        System.out.printf("The sales tax on $%.2f in %s is $%.2f.\n", saleAmount, state.getName(), tax);
    }
}
