package com.xencypro.xencypass

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.xencypro.xencypass.databinding.ActivityVaultBinding

class VaultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVaultBinding
    private var isFabExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVaultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabs()
        setupFabMenu()
        showTabContent(Tab.PASSWORDS)

        binding.searchField.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.searchField.clearFocus()
            }
        }
    }

    private fun setupTabs() {
        binding.tabPasswords.setOnClickListener { showTabContent(Tab.PASSWORDS) }
        binding.tabWallet.setOnClickListener { showTabContent(Tab.WALLET) }
        binding.tabNotes.setOnClickListener { showTabContent(Tab.NOTES) }
    }

    private fun showTabContent(tab: Tab) {
        binding.tabPasswords.isActivated = tab == Tab.PASSWORDS
        binding.tabWallet.isActivated = tab == Tab.WALLET
        binding.tabNotes.isActivated = tab == Tab.NOTES

        binding.tabPasswords.setTextColor(getTabTextColor(tab == Tab.PASSWORDS))
        binding.tabWallet.setTextColor(getTabTextColor(tab == Tab.WALLET))
        binding.tabNotes.setTextColor(getTabTextColor(tab == Tab.NOTES))

        binding.contentContainer.removeAllViews()
        val items = when (tab) {
            Tab.PASSWORDS -> listOf(
                VaultEntry("Work Account", "Google / xencypro@gmail.com", "Strong"),
                VaultEntry("Bank App", "Chase Mobile", "Medium")
            )
            Tab.WALLET -> listOf(
                VaultEntry("Visa Platinum", "**** 1234", "Active"),
                VaultEntry("Crypto Safe", "Ethereum Wallet", "Protected")
            )
            Tab.NOTES -> listOf(
                VaultEntry("Recovery Phrase", "12-word backup stored", "Private"),
                VaultEntry("Wi-Fi Pass", "Home network credentials", "Secure")
            )
        }

        items.forEach { binding.contentContainer.addView(createRow(it)) }
    }

    private fun getTabTextColor(active: Boolean): Int {
        val colorRes = if (active) R.color.white else R.color.text_secondary
        return ContextCompat.getColor(this, colorRes)
    }

    private fun createRow(entry: VaultEntry): View {
        val item = layoutInflater.inflate(R.layout.item_vault_entry, binding.contentContainer, false)
        val title = item.findViewById<TextView>(R.id.itemTitle)
        val subtitle = item.findViewById<TextView>(R.id.itemSubtitle)
        val badge = item.findViewById<TextView>(R.id.itemBadge)

        title.text = entry.title
        subtitle.text = entry.subtitle
        badge.text = entry.badge
        return item
    }

    private fun setupFabMenu() {
        binding.mainFab.setOnClickListener {
            toggleFabMenu()
        }
        binding.fabAddPassword.setOnClickListener {
            startActivity(Intent(this, AddPasswordActivity::class.java))
            toggleFabMenu()
        }
        binding.fabAddWallet.setOnClickListener {
            Toast.makeText(this, "Add Wallet placeholder", Toast.LENGTH_SHORT).show()
            toggleFabMenu()
        }
        binding.fabAddNote.setOnClickListener {
            Toast.makeText(this, "Add Note placeholder", Toast.LENGTH_SHORT).show()
            toggleFabMenu()
        }
    }

    private fun toggleFabMenu() {
        isFabExpanded = !isFabExpanded
        val visibility = if (isFabExpanded) View.VISIBLE else View.GONE
        binding.fabAddPassword.visibility = visibility
        binding.fabAddWallet.visibility = visibility
        binding.fabAddNote.visibility = visibility
    }

    private enum class Tab {
        PASSWORDS,
        WALLET,
        NOTES
    }

    private data class VaultEntry(
        val title: String,
        val subtitle: String,
        val badge: String
    )
}
