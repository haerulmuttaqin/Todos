package id.haeworks.core.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.haeworks.core.R
import id.haeworks.core.model.SnackbarState
import id.haeworks.core.model.SnackbarType
import id.haeworks.core.util.extension.ifNullOrEmpty
import id.haeworks.core.util.extension.orResourceStringEmpty

@Composable
fun DefaultSnackbar(
    modifier: Modifier = Modifier,
    data: SnackbarData,
    state: SnackbarState,
) {
    val context = LocalContext.current
    val snackbarData = data.visuals.toDefaultSnackBarVisual(context, state)
    val actionLabel = snackbarData.actionLabel
    var contentColor = snackbarData.type?.contentColor ?: R.color.neutral_soft_color
    var containerColor = snackbarData.type?.containerColor ?: R.color.neutral_soft_bg

    if (snackbarData.type == null) {
        contentColor =
            if (snackbarData.isSuccess) R.color.success_soft_color else R.color.danger_soft_color
        containerColor =
            if (snackbarData.isSuccess) R.color.success_soft_bg else R.color.danger_soft_bg
    }

    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = colorResource(id = contentColor)),
                onClick = { data.performAction() },
                content = {
                    Text(
                        text = actionLabel,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(id = contentColor)
                        )
                    )
                }
            )
        }
    } else {
        null
    }
    val dismissActionComposable: (@Composable () -> Unit)? =
        if (snackbarData.withDismissAction) {
            @Composable {
                IconButton(
                    onClick = { data.dismiss() },
                    content = {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = colorResource(id = contentColor)
                        )
                    }
                )
            }
        } else {
            null
        }
    Box(modifier = modifier.padding(16.dp)) {
        Snackbar(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .shadow(2.dp),
            contentColor = colorResource(id = contentColor),
            containerColor = colorResource(id = containerColor),
            actionContentColor = colorResource(id = contentColor),
            action = actionComposable,
            dismissActionContentColor = colorResource(id = contentColor),
            dismissAction = dismissActionComposable,
            shape = RoundedCornerShape(6.dp),
        ) {
            Text(
                modifier = Modifier.padding(bottom = 0.dp),
                text = snackbarData.message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium, color = colorResource(id = contentColor)
                )
            )
        }
    }
}

data class DefaultSnackbarVisuals(
    override val message: String,
    val isSuccess: Boolean,
    val type: SnackbarType? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals

fun SnackbarVisuals.toDefaultSnackBarVisual(context: Context, state: SnackbarState) =
    DefaultSnackbarVisuals(
        message = state.message.ifNullOrEmpty { context.getString(state.messageId.orResourceStringEmpty()) },
        isSuccess = state.isSuccess,
        type = state.type,
        duration = this.duration,
        actionLabel = this.actionLabel,
        withDismissAction = this.withDismissAction,
    )

suspend fun SnackbarHostState.showDefaultSnackbar(
    context: Context,
    snackbar: SnackbarState,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = SnackbarDuration.Short,
): SnackbarResult {
    return this.showSnackbar(
        DefaultSnackbarVisuals(
            message = snackbar.message.ifNullOrEmpty { context.getString(snackbar.messageId.orResourceStringEmpty()) },
            isSuccess = snackbar.isSuccess,
            type = snackbar.type,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = duration
        ),
    )
}