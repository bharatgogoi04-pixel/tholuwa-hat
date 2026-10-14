import test from 'node:test';
import assert from 'node:assert/strict';
import { subscriptionPricePaise, canCreateShop, referralEligible, REFERRAL_REWARD_PAISE, WITHDRAWAL_MIN_PAISE, canTransitionOrder } from '../src/domain.js';

test('subscription amounts use integer paise',()=>{assert.equal(subscriptionPricePaise(true),12000);assert.equal(subscriptionPricePaise(false),9900)});
test('maximum five shops per verified phone',()=>{assert.equal(canCreateShop(0),true);assert.equal(canCreateShop(4),true);assert.equal(canCreateShop(5),false)});
test('referral reward and withdrawal threshold',()=>{assert.equal(REFERRAL_REWARD_PAISE,2000);assert.equal(WITHDRAWAL_MIN_PAISE,50000)});
test('referral requires qualifying new seller payment',()=>{assert.equal(referralEligible({isNewSeller:true,paymentVerified:true,amountPaise:12000,selfReferral:false,duplicateAccount:false,shopApproved:true}),true);assert.equal(referralEligible({isNewSeller:true,paymentVerified:false,amountPaise:12000,selfReferral:false,duplicateAccount:false,shopApproved:true}),false)});
test('order state transitions reject invalid transitions',()=>{assert.equal(canTransitionOrder('placed','accepted'),true);assert.equal(canTransitionOrder('completed','cancelled'),false)});
