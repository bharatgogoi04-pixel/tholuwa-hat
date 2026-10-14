export const FIRST_SHOP_PRICE_PAISE = 12000;
export const RENEWAL_PRICE_PAISE = 9900;
export const REFERRAL_REWARD_PAISE = 2000;
export const WITHDRAWAL_MIN_PAISE = 50000;
export const MAX_SHOPS_PER_PHONE = 5;

export function subscriptionPricePaise(isFirstShopPayment) { return isFirstShopPayment ? FIRST_SHOP_PRICE_PAISE : RENEWAL_PRICE_PAISE; }
export function canCreateShop(existingShopsForPhone) { return Number.isInteger(existingShopsForPhone) && existingShopsForPhone >= 0 && existingShopsForPhone < MAX_SHOPS_PER_PHONE; }
export function referralEligible({ isNewSeller, paymentVerified, amountPaise, selfReferral=false, duplicateAccount=false, shopApproved=false }) {
  return Boolean(isNewSeller && paymentVerified && amountPaise === FIRST_SHOP_PRICE_PAISE && !selfReferral && !duplicateAccount && shopApproved);
}
export function ledgerBalance(entries) {
  return entries.reduce((sum, e) => {
    if (!Number.isSafeInteger(e.amountPaise) || e.amountPaise < 0) throw new TypeError('ledger amount must be a non-negative integer in paise');
    if (!['credit','debit'].includes(e.direction)) throw new TypeError('invalid ledger direction');
    return sum + (e.direction === 'credit' ? e.amountPaise : -e.amountPaise);
  }, 0);
}
const transitions = {
  placed: ['accepted','rejected','cancelled'], accepted: ['preparing','cancelled'], preparing: ['ready_for_pickup','dispatched','cancelled'],
  ready_for_pickup: ['completed','cancelled'], dispatched: ['completed'], rejected: [], cancelled: [], completed: []
};
export function canTransitionOrder(from, to, actor='seller') {
  if (!transitions[from]?.includes(to)) return false;
  if (to === 'rejected' && actor !== 'seller') return false;
  if (to === 'completed' && !['seller','customer','admin'].includes(actor)) return false;
  return true;
}
export function assertPaise(value) { if (!Number.isSafeInteger(value) || value < 0) throw new TypeError('money must be a non-negative integer in paise'); return value; }
