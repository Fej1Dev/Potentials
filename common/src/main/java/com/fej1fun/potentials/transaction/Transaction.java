/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fej1fun.potentials.transaction;

import com.fej1fun.potentials.platform.TransactionManager;
import org.jetbrains.annotations.ApiStatus;

/**
 * A global operation where participants guarantee atomicity: either the whole operation succeeds,
 * or it is completely aborted and rolled back.
 *
 * <p>One can imagine that transactions are like video game checkpoints.
 * <ul>
 *     <li>{@linkplain #openRoot Opening a transaction} with a try-with-resources block creates a checkpoint.</li>
 *     <li>Modifications to game state can then happen.</li>
 *     <li>Calling {@link #commit} validates the modifications that happened during the transaction,
 *     essentially discarding the checkpoint.</li>
 *     <li>Calling {@link #abort} or doing nothing and letting the transaction be {@linkplain #close closed} at the end
 *     of the try-with-resources block cancels any modification that happened during the transaction,
 *     reverting to the checkpoint.</li>
 *     <li>Calling {@link #openNested} on a transaction creates a new nested transaction, i.e. a new checkpoint with the current state.
 *     Committing a nested transaction will validate the changes that happened, but they may
 *     still be cancelled later if a parent transaction is cancelled.
 *     Aborting a nested transaction immediately reverts the changes - cancelling any modification made after the call
 *     to {@link #openNested}.</li>
 * </ul>
 *
 * <p>This is illustrated in the following example.
 * <pre>{@code
 * try (Transaction outerTransaction = Transaction.openOuter()) {
 *     // (A) some transaction operations
 *     try (Transaction nestedTransaction = outerTransaction.openNested()) {
 *         // (B) more operations
 *         nestedTransaction.commit(); // Validate the changes that happened in this transaction.
 *                                     // This is a nested transaction, so changes will only be applied if the outer
 *                                     // transaction is committed too.
 *     }
 *     // (C) even more operations
 *     outerTransaction.commit(); // This is an outer transaction: changes (A), (B) and (C) are applied.
 * }
 * // If we hadn't committed the outerTransaction, all changes (A), (B) and (C) would have been reverted.
 * }</pre>
 *
 * <p>This is very low-level for most applications, and most participants should subclass {@link SnapshotParticipant}
 * that will take care of properly maintaining their state.
 *
 * <p>Participants should generally be passed a {@link TransactionContext} parameter instead of the full {@code Transaction},
 * to make sure they don't call {@link #abort}, {@link #commit} or {@link #close} mistakenly.
 *
 * <p>Every transaction is only valid on the thread it was opened on,
 * and attempts to use it on another thread will throw an exception.
 * Consequently, transactions can be concurrent across multiple threads, as long as they don't share any state.
 * <p>
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
@ApiStatus.NonExtendable
public interface Transaction extends TransactionContext, AutoCloseable {

    static Transaction openRoot() {
        return TransactionManager.open(null);
    }

    static Lifecycle getLifecycle() {
        return TransactionManager.getLifecycle();
    }

    void abort();

    void commit();

    enum Lifecycle {
        /**
         * No transaction is currently open or closing.
         */
        NONE,
        /**
         * A transaction is currently open.
         */
        OPEN,
        /**
         * The current transaction is invoking its close callbacks.
         */
        CLOSING,
        /**
         * The current transaction is invoking its outer/root close callbacks.
         */
        OUTER_CLOSING
    }
}
