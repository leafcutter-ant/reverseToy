.class final Lcom/adhoc/p$2;
.super Ljava/lang/Object;

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/adhoc/p;->a(Ljava/lang/String;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = null
.end annotation


# instance fields
.field final synthetic a:Ljava/lang/String;

.field final synthetic b:Ljava/lang/String;


# direct methods
.method constructor <init>(Ljava/lang/String;Ljava/lang/String;)V
    .registers 3

    iput-object p1, p0, Lcom/adhoc/p$2;->a:Ljava/lang/String;

    iput-object p2, p0, Lcom/adhoc/p$2;->b:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 6

    :try_start_0
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0xb

    if-lt v0, v1, :cond_35

    new-instance v1, Landroid/os/Bundle;

    invoke-direct {v1}, Landroid/os/Bundle;-><init>()V

    const-string v0, "key"

    iget-object v2, p0, Lcom/adhoc/p$2;->a:Ljava/lang/String;

    invoke-virtual {v1, v0, v2}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    const-string v0, "value"

    iget-object v2, p0, Lcom/adhoc/p$2;->b:Ljava/lang/String;

    invoke-virtual {v1, v0, v2}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    invoke-static {}, Lcom/adhoc/p;->c()Landroid/net/Uri;

    move-result-object v0

    if-eqz v0, :cond_35

    sget-object v0, Lcom/adhoc/p;->a:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    invoke-static {}, Lcom/adhoc/p;->c()Landroid/net/Uri;

    move-result-object v2

    const-string v3, "put_string"

    const/4 v4, 0x0

    invoke-virtual {v0, v2, v3, v4, v1}, Landroid/content/ContentResolver;->call(Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Landroid/os/Bundle;)Landroid/os/Bundle;
    :try_end_35
    .catch Ljava/lang/Throwable; {:try_start_0 .. :try_end_35} :catch_36

    :cond_35
    :goto_35
    return-void

    :catch_36
    move-exception v0

    invoke-static {v0}, Lcom/adhoc/al;->b(Ljava/lang/Throwable;)V

    goto :goto_35
.end method
