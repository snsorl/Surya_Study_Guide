// ========================================================
// TASK 1: Product Interface and DTO Declarations
// ========================================================

export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    createdDate: Date;
}

// TODO: Declare 'CreateProductDto' using Omit utility type to strip id and createdDate
export type CreateProductDto = any; 

// TODO: Declare 'UpdateProductDto' using Partial utility type on CreateProductDto
export type UpdateProductDto = any; 


// ========================================================
// TASK 2: Generic Paginated Container Class
// ========================================================

// TODO: Make this class Generic to support any type shape T
export class PaginatedResponse {
    // TODO: Declare a private property 'items' of type T[] and a totalCount number
    
    // TODO: Write constructor and getters
}


// ========================================================
// TASK 3: Deprecated Method Decorator
// ========================================================

export function Deprecated(warningMessage: string) {
    return function(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
        const originalMethod = descriptor.value;

        // TODO: Intercept method value descriptor to log warningMessage before execution
        descriptor.value = function(...args: any[]) {
            console.warn(`[WARNING] Deprecated method '${propertyKey}' called: ${warningMessage}`);
            return originalMethod.apply(this, args);
        };
    };
}


// ========================================================
// Verification Test Harness (Instructor use/validation)
// ========================================================
class CatalogService {
    @Deprecated("Use new search endpoint instead.")
    fetchLegacyCatalog() {
        return ["Old Laptop", "Old Desktop"];
    }
}

const service = new CatalogService();
console.log("Fetching legacy list:");
service.fetchLegacyCatalog();
